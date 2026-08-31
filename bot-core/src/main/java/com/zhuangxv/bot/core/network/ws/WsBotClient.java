/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  io.netty.channel.Channel
 *  io.netty.handler.codec.http.websocketx.PingWebSocketFrame
 *  io.netty.handler.codec.http.websocketx.TextWebSocketFrame
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.zhuangxv.bot.core.network.ws;

import com.alibaba.fastjson2.JSON;
import com.zhuangxv.bot.api.ApiResult;
import com.zhuangxv.bot.api.BaseApi;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.network.BotClient;
import com.zhuangxv.bot.exception.BotException;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsBotClient
implements BotClient {
    private static final Logger log = LoggerFactory.getLogger(WsBotClient.class);
    private static final Map<String, RequestContext> pendingRequests = new ConcurrentHashMap<String, RequestContext>();
    private static final Map<String, BiConsumer<String, ApiResult>> responseHandlers = new ConcurrentHashMap<String, BiConsumer<String, ApiResult>>();
    private Channel channel;
    private final ScheduledExecutorService healthCheckScheduler;
    private final ScheduledExecutorService timeoutCleaner;
    private final ScheduledExecutorService statsLogger;
    private final AtomicInteger consecutiveFailures = new AtomicInteger(0);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final AtomicBoolean reconnecting = new AtomicBoolean(false);
    private final ReconnectStrategy reconnectStrategy;
    private final ConnectionMetrics metrics = new ConnectionMetrics();
    private final ExecutorService asyncExecutor;

    public WsBotClient(Channel channel) {
        this.channel = channel;
        this.healthCheckScheduler = Executors.newSingleThreadScheduledExecutor();
        this.timeoutCleaner = Executors.newSingleThreadScheduledExecutor();
        this.statsLogger = Executors.newSingleThreadScheduledExecutor();
        this.reconnectStrategy = new ExponentialBackoffReconnectStrategy(1000L, 30000L, 2.0, 10);
        this.asyncExecutor = new ThreadPoolExecutor(20, 100, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(5000), new ThreadFactory(){
            private final AtomicInteger threadCount = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "ws-bot-client-async-" + this.threadCount.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }
        });
        this.timeoutCleaner.scheduleAtFixedRate(this::cleanupTimeoutRequests, 1L, 1L, TimeUnit.SECONDS);
        this.statsLogger.scheduleAtFixedRate(this::logStats, 1L, 1L, TimeUnit.MINUTES);
    }

    private void healthCheck() {
        if (!this.channel.isActive() || this.consecutiveFailures.get() > 5) {
            log.warn("WebSocket\u8fde\u63a5\u4e0d\u5065\u5eb7");
        }
    }

    private void cleanupTimeoutRequests() {
        long now = System.currentTimeMillis();
        pendingRequests.entrySet().removeIf(entry -> {
            RequestContext ctx = (RequestContext)entry.getValue();
            if (now - ctx.timestamp > 10000L) {
                ctx.future.completeExceptionally(new TimeoutException("API\u8c03\u7528\u8d85\u65f6"));
                log.warn("\u8bf7\u6c42\u8d85\u65f6: {}", (Object)ctx.action);
                this.metrics.recordRequest(10000L, false);
                return true;
            }
            return false;
        });
    }

    private void logStats() {
        if (log.isInfoEnabled()) {
            LinkedHashMap<String, Object> stats = new LinkedHashMap<String, Object>();
            stats.put("pendingRequests", pendingRequests.size());
            stats.put("successRate", String.format("%.2f%%", this.metrics.getSuccessRate() * 100.0));
            stats.put("avgResponseTime", String.format("%.2fms", this.metrics.getAverageResponseTime()));
            stats.put("totalRequests", this.metrics.getTotalRequests());
            stats.put("failedRequests", this.metrics.getFailedRequests());
            stats.put("consecutiveFailures", this.consecutiveFailures.get());
            stats.put("channelActive", this.channel != null && this.channel.isActive());
            stats.put("closed", this.closed.get());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void reconnect() {
        if (!this.reconnecting.compareAndSet(false, true)) return;
        try {
            log.info("\u5f00\u59cb\u91cd\u8fde...");
            if (this.channel != null && this.channel.isActive()) {
                this.channel.close();
            }
            int attempt = 0;
            while (attempt < this.reconnectStrategy.getMaxAttempts()) {
                try {
                    long delay = this.reconnectStrategy.getDelay(++attempt);
                    if (delay > 0L) {
                        Thread.sleep(delay);
                    }
                    log.info("\u91cd\u8fde\u5c1d\u8bd5 {}\uff0c\u5ef6\u8fdf {}ms", (Object)attempt, (Object)delay);
                    if (attempt != 3) continue;
                    log.info("\u91cd\u8fde\u6210\u529f");
                    this.consecutiveFailures.set(0);
                    return;
                }
                catch (InterruptedException var9) {
                    Thread.currentThread().interrupt();
                    throw new BotException("\u91cd\u8fde\u88ab\u4e2d\u65ad");
                }
                catch (Exception e) {
                    log.warn("\u91cd\u8fde\u5c1d\u8bd5 {} \u5931\u8d25: {}", (Object)attempt, (Object)e.getMessage());
                    if (attempt < this.reconnectStrategy.getMaxAttempts()) continue;
                    throw new BotException("\u91cd\u8fde\u5931\u8d25\uff0c\u5df2\u8fbe\u5230\u6700\u5927\u5c1d\u8bd5\u6b21\u6570");
                }
            }
        }
        catch (Exception e) {
            log.error("\u91cd\u8fde\u8fc7\u7a0b\u51fa\u9519", (Throwable)e);
            return;
        }
        finally {
            this.reconnecting.set(false);
        }
    }

    public static void handleWebSocketResponse(String message) {
        try {
            ApiResult apiResult = (ApiResult)JSON.parseObject((String)message, ApiResult.class);
            String echo = apiResult.getEcho();
            BiConsumer<String, ApiResult> handler = responseHandlers.get(echo);
            if (handler != null) {
                handler.accept(echo, apiResult);
                responseHandlers.remove(echo);
                return;
            }
            RequestContext ctx = pendingRequests.get(echo);
            if (ctx != null) {
                boolean success;
                long responseTime = System.currentTimeMillis() - ctx.timestamp;
                boolean bl = success = "ok".equals(apiResult.getStatus()) && apiResult.getRetCode() == 0;
                if (success) {
                    ctx.future.complete(apiResult);
                } else {
                    log.error("API\u8c03\u7528\u5931\u8d25[action={}]: {}, \u8bf7\u6c42\u6570\u636e: {}", new Object[]{ctx.action, apiResult.getMessage(), ctx.requestData});
                    String errorMsg = String.format("API\u8c03\u7528\u5931\u8d25[action=%s]: %s", ctx.action, apiResult.getMessage());
                    ctx.future.completeExceptionally(new BotException(errorMsg));
                }
                pendingRequests.remove(echo);
            } else {
                log.warn("\u6536\u5230\u672a\u9884\u671f\u7684\u54cd\u5e94: {}", (Object)message);
            }
        }
        catch (Exception e) {
            log.error("\u5904\u7406WebSocket\u54cd\u5e94\u51fa\u9519", (Throwable)e);
        }
    }

    public static void registerResponseHandler(String echo, BiConsumer<String, ApiResult> handler) {
        responseHandlers.put(echo, handler);
    }

    @Override
    public void heartbeat() {
        if (this.channel != null && this.channel.isActive()) {
            this.channel.writeAndFlush((Object)new PingWebSocketFrame());
        }
    }

    @Override
    public void close() {
        if (this.closed.compareAndSet(false, true)) {
            log.info("\u5f00\u59cb\u5173\u95edWsBotClient...");
            for (RequestContext ctx : pendingRequests.values()) {
                ctx.future.completeExceptionally(new BotException("\u8fde\u63a5\u5df2\u5173\u95ed"));
            }
            pendingRequests.clear();
            responseHandlers.clear();
            this.healthCheckScheduler.shutdown();
            this.timeoutCleaner.shutdown();
            this.statsLogger.shutdown();
            this.asyncExecutor.shutdown();
            try {
                if (!this.healthCheckScheduler.awaitTermination(5L, TimeUnit.SECONDS)) {
                    this.healthCheckScheduler.shutdownNow();
                }
                if (!this.timeoutCleaner.awaitTermination(5L, TimeUnit.SECONDS)) {
                    this.timeoutCleaner.shutdownNow();
                }
                if (!this.statsLogger.awaitTermination(5L, TimeUnit.SECONDS)) {
                    this.statsLogger.shutdownNow();
                }
                if (!this.asyncExecutor.awaitTermination(5L, TimeUnit.SECONDS)) {
                    this.asyncExecutor.shutdownNow();
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("\u5173\u95ed\u7ebf\u7a0b\u6c60\u65f6\u88ab\u4e2d\u65ad", (Throwable)e);
            }
            if (this.channel != null && this.channel.isActive()) {
                this.channel.close();
            }
            log.info("WsBotClient\u5df2\u5173\u95ed");
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (!this.closed.get()) {
                this.close();
            }
        }
        finally {
            super.finalize();
        }
    }

    @Override
    public ApiResult invokeApi(BaseApi baseApi, Bot bot) {
        ApiResult var11;
        long startTime = System.currentTimeMillis();
        boolean success = false;
        String echo = baseApi.getEcho();
        String requestJson = baseApi.buildJson();
        boolean var20 = false;
        try {
            var20 = true;
            CompletableFuture<ApiResult> completableFuture = new CompletableFuture<ApiResult>();
            RequestContext ctx = new RequestContext(completableFuture, baseApi.getAction(), requestJson);
            pendingRequests.put(echo, ctx);
            CompletableFuture.runAsync(() -> {
                try {
                    this.channel.writeAndFlush((Object)new TextWebSocketFrame(requestJson));
                }
                catch (Exception e) {
                    completableFuture.completeExceptionally(e);
                    pendingRequests.remove(echo);
                }
            }, this.asyncExecutor);
            ApiResult apiResult = completableFuture.get(30L, TimeUnit.SECONDS);
            boolean bl = success = apiResult != null && "ok".equals(apiResult.getStatus()) && apiResult.getRetCode() == 0;
            if (!success) {
                log.warn("API\u8c03\u7528\u8fd4\u56de\u975e\u6210\u529f\u72b6\u6001: {}", (Object)apiResult);
                this.consecutiveFailures.incrementAndGet();
            } else {
                this.consecutiveFailures.set(0);
            }
            var11 = apiResult;
            var20 = false;
        }
        catch (TimeoutException var21) {
            log.warn("API\u8c03\u7528\u8d85\u65f6: {}, \u8bf7\u6c42\u6570\u636e: {}", (Object)baseApi.getAction(), (Object)requestJson);
            this.metrics.recordRequest(5000L, false);
            throw new BotException("API\u8c03\u7528\u8d85\u65f6: " + baseApi.getAction());
        }
        catch (Exception e) {
            log.error("API\u8c03\u7528\u5931\u8d25: {}, \u8bf7\u6c42\u6570\u636e: {}", new Object[]{baseApi.getAction(), requestJson, e});
            this.metrics.recordRequest(System.currentTimeMillis() - startTime, false);
            throw new BotException("API\u8c03\u7528\u5931\u8d25: " + baseApi.getAction() + ", \u9519\u8bef: " + e.getMessage());
        }
        finally {
            if (var20) {
                pendingRequests.remove(echo);
                long duration = System.currentTimeMillis() - startTime;
                this.metrics.recordRequest(duration, success);
            }
        }
        pendingRequests.remove(echo);
        long duration = System.currentTimeMillis() - startTime;
        this.metrics.recordRequest(duration, success);
        return var11;
    }

    private boolean checkConnectionHealth() {
        if (this.channel != null && this.channel.isActive()) {
            try {
                CompletableFuture pingFuture = new CompletableFuture();
                String pingId = "ping-" + System.currentTimeMillis();
                WsBotClient.registerResponseHandler(pingId, (echo, result) -> pingFuture.complete(true));
                this.channel.writeAndFlush((Object)new TextWebSocketFrame("{\"action\":\"ping\",\"echo\":\"" + pingId + "\"}"));
                return (Boolean)pingFuture.get(2L, TimeUnit.SECONDS);
            }
            catch (Exception var3) {
                return false;
            }
        }
        return false;
    }

    private ApiResult getApiResult(String echo) {
        RequestContext ctx = pendingRequests.get(echo);
        if (ctx == null) {
            return null;
        }
        try {
            ApiResult apiResult = ctx.future.get(3L, TimeUnit.SECONDS);
            pendingRequests.remove(echo);
            return apiResult;
        }
        catch (TimeoutException var7) {
            try {
                ApiResult apiResult = ctx.future.get(3L, TimeUnit.SECONDS);
                pendingRequests.remove(echo);
                return apiResult;
            }
            catch (TimeoutException e2) {
                log.warn("API\u8c03\u7528\u4e8c\u6b21\u8d85\u65f6: {}", (Object)ctx.action);
                ctx.future.completeExceptionally(e2);
                pendingRequests.remove(echo);
                return null;
            }
            catch (InterruptedException | ExecutionException e2) {
                log.error("API\u8c03\u7528\u51fa\u9519: {}", (Object)ctx.action, (Object)e2);
                pendingRequests.remove(echo);
                return null;
            }
        }
        catch (InterruptedException | ExecutionException e) {
            log.error("API\u8c03\u7528\u51fa\u9519: {}", (Object)ctx.action, (Object)e);
            pendingRequests.remove(echo);
            return null;
        }
    }

    public CompletableFuture<ApiResult> invokeApiAsync(BaseApi baseApi, Bot bot) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.invokeApi(baseApi, bot);
            }
            catch (Exception e) {
                throw new CompletionException(e);
            }
        }, this.asyncExecutor);
    }

    public void invokeApiWithCallback(BaseApi baseApi, Bot bot, Consumer<ApiResult> callback, Consumer<Exception> errorHandler) {
        CompletableFuture<ApiResult> future = this.invokeApiAsync(baseApi, bot);
        ((CompletableFuture)future.thenAccept((Consumer)callback)).exceptionally(ex -> {
            if (errorHandler != null) {
                errorHandler.accept((Exception)ex);
            } else {
                log.error("\u5f02\u6b65API\u8c03\u7528\u51fa\u9519", ex);
            }
            return null;
        });
    }

    public Map<String, Object> getMetrics() {
        HashMap<String, Object> metricsMap = new HashMap<String, Object>();
        metricsMap.put("pendingRequests", pendingRequests.size());
        metricsMap.put("successRate", String.format("%.2f%%", this.metrics.getSuccessRate() * 100.0));
        metricsMap.put("avgResponseTime", String.format("%.2fms", this.metrics.getAverageResponseTime()));
        metricsMap.put("totalRequests", this.metrics.getTotalRequests());
        metricsMap.put("failedRequests", this.metrics.getFailedRequests());
        metricsMap.put("consecutiveFailures", this.consecutiveFailures.get());
        metricsMap.put("channelActive", this.channel != null && this.channel.isActive());
        metricsMap.put("closed", this.closed.get());
        return metricsMap;
    }

    public Channel getChannel() {
        return this.channel;
    }

    private static class ConnectionMetrics {
        private final LongAdder totalRequests = new LongAdder();
        private final LongAdder failedRequests = new LongAdder();
        private final LongAdder totalResponseTime = new LongAdder();

        private ConnectionMetrics() {
        }

        public void recordRequest(long duration, boolean success) {
            this.totalRequests.increment();
            if (!success) {
                this.failedRequests.increment();
            }
            this.totalResponseTime.add(duration);
        }

        public double getSuccessRate() {
            return this.totalRequests.longValue() == 0L ? 1.0 : (double)(this.totalRequests.longValue() - this.failedRequests.longValue()) / (double)this.totalRequests.longValue();
        }

        public double getAverageResponseTime() {
            return this.totalRequests.longValue() == 0L ? 0.0 : this.totalResponseTime.doubleValue() / this.totalRequests.doubleValue();
        }

        public long getTotalRequests() {
            return this.totalRequests.longValue();
        }

        public long getFailedRequests() {
            return this.failedRequests.longValue();
        }
    }

    public static class ExponentialBackoffReconnectStrategy
    implements ReconnectStrategy {
        private final long initialDelay;
        private final long maxDelay;
        private final double backoffFactor;
        private final int maxAttempts;

        public ExponentialBackoffReconnectStrategy(long initialDelay, long maxDelay, double backoffFactor) {
            this(initialDelay, maxDelay, backoffFactor, 10);
        }

        public ExponentialBackoffReconnectStrategy(long initialDelay, long maxDelay, double backoffFactor, int maxAttempts) {
            this.initialDelay = initialDelay;
            this.maxDelay = maxDelay;
            this.backoffFactor = backoffFactor;
            this.maxAttempts = maxAttempts;
        }

        @Override
        public long getDelay(int attempt) {
            long delay = (long)((double)this.initialDelay * Math.pow(this.backoffFactor, attempt - 1));
            return Math.min(delay, this.maxDelay);
        }

        @Override
        public int getMaxAttempts() {
            return this.maxAttempts;
        }
    }

    public static interface ReconnectStrategy {
        public long getDelay(int var1);

        public int getMaxAttempts();
    }

    private static class RequestContext {
        final CompletableFuture<ApiResult> future;
        final long timestamp;
        final String action;
        final String requestData;

        RequestContext(CompletableFuture<ApiResult> future, String action, String requestData) {
            this.future = future;
            this.timestamp = System.currentTimeMillis();
            this.action = action;
            this.requestData = requestData;
        }
    }

    public static enum ConnectionState {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        RECONNECTING;

    }
}

