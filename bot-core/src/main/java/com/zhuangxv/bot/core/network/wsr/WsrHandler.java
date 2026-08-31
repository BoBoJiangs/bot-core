//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.core.network.wsr;

import com.zhuangxv.bot.config.BotConfig;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.BotContext;
import com.zhuangxv.bot.core.Group;
import com.zhuangxv.bot.core.component.BotDispatcher;
import com.zhuangxv.bot.core.component.BotFactory;
import com.zhuangxv.bot.core.network.BotClient;
import com.zhuangxv.bot.core.network.ws.WsBotClient;
import com.zhuangxv.bot.event.message.QQConnectedEvent;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

public class WsrHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger log = LoggerFactory.getLogger(WsrHandler.class);
    private final BotConfig botConfig;
    private final BotDispatcher botDispatcher;
    private final Map<Long, Bot> bots;

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest)msg;
            if (!request.decoderResult().isSuccess()) {
                this.sendResponse(ctx, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.BAD_REQUEST, ctx.alloc().buffer()));
                return;
            }

            if (!HttpMethod.GET.equals(request.method())) {
                this.sendResponse(ctx, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.FORBIDDEN, ctx.alloc().buffer()));
                return;
            }

            String authorization = request.headers().get("Authorization");
            if (StringUtils.isEmpty(authorization)) {
                this.sendResponse(ctx, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.BAD_REQUEST, ctx.alloc().buffer()));
                ctx.close();
                return;
            }

            boolean isReturn = false;
            Map<String, BotContext> beans = BotFactory.getBeansByClass(BotContext.class);
            if (beans != null) {
                long qq;
                try {
                    qq = Long.parseLong(((FullHttpRequest)msg).headers().get("X-Self-ID"));
                } catch (Exception var11) {
                    return;
                }

                for(BotContext botContext : beans.values()) {
                    isReturn = !botContext.approve(qq, authorization.replace("Token ", ""));
                }
            } else {
                isReturn = !this.botConfig.getAccessToken().equals(authorization.replace("Token ", ""));
            }

            if (isReturn) {
                return;
            }

            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(this.getWebSocketLocation(request), (String)null, true, 5242880);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(request);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), request);
                BotClient botClient = new WsBotClient(ctx.channel());
                Bot bot = new Bot(this.botConfig, botClient);
                (new Thread(() -> {
                    try {
                        bot.flushBotInfo();
                        log.info(String.format("[%s]QQ服务器连接成功! QQ server connected!", bot.getBotName()));
                        if (bot.getBotId() == 0L || StringUtils.isEmpty(bot.getBotName())) {
                            log.error("ws反向连接失败.");
                            ctx.close();
                            return;
                        }

                        Map<String, BotContext> beanMap = BotFactory.getBeansByClass(BotContext.class);
                        if (beanMap != null) {
                            for(BotContext botContext : beanMap.values()) {
                                botContext.connected(bot);
                            }
                        }

                        this.bots.put(bot.getBotId(), bot);
                        ApplicationContext ctxSpring = BotFactory.getApplicationContext();
                        ctxSpring.publishEvent(new QQConnectedEvent(this, bot));

                        try {
                            bot.flushFriends();

                            for(Group group : bot.flushGroups()) {
                                bot.flushGroupMembers(group);
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }

                        bot.getCompletableFuture().complete(1L);
                    } catch (Exception e) {
                        ctx.close();
                        log.error("ws反向连接失败. " + e.getMessage(), e);
                    }

                })).start();
            }
        } else if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame)msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame)frame;
                this.botDispatcher.handle(textFrame.text());
            } else if (frame instanceof CloseWebSocketFrame) {
                ctx.close();
            }
        }

    }

    private String getWebSocketLocation(FullHttpRequest request) {
        String location = request.headers().get(HttpHeaderNames.HOST) + "/websocket";
        return "ws://" + location;
    }

    private void sendResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp) {
        HttpResponseStatus status = resp.status();
        if (status != HttpResponseStatus.OK) {
            ByteBufUtil.writeUtf8(resp.content(), status.toString());
            HttpUtil.setContentLength(req, (long)resp.content().readableBytes());
        }

        boolean keepAlive = HttpUtil.isKeepAlive(req) && status == HttpResponseStatus.OK;
        HttpUtil.setKeepAlive(req, keepAlive);
        ChannelFuture future = ctx.write(resp);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }

    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(new PingWebSocketFrame());
            }
        }

        super.userEventTriggered(ctx, evt);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        for(Map.Entry<Long, Bot> botEntry : this.bots.entrySet()) {
            BotClient botClient = ((Bot)botEntry.getValue()).getBotClient();
            if (botClient instanceof WsBotClient && ((WsBotClient)botClient).getChannel().id().asLongText().equals(ctx.channel().id().asLongText())) {
                this.bots.remove(botEntry.getKey());
            }
        }

    }

    public WsrHandler(BotConfig botConfig, BotDispatcher botDispatcher, Map<Long, Bot> bots) {
        this.botConfig = botConfig;
        this.botDispatcher = botDispatcher;
        this.bots = bots;
    }
}
