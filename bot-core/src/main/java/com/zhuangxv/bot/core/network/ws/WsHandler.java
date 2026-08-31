//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.core.network.ws;

import com.zhuangxv.bot.config.BotConfig;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.BotContext;
import com.zhuangxv.bot.core.Group;
import com.zhuangxv.bot.core.component.BotDispatcher;
import com.zhuangxv.bot.core.component.BotFactory;
import com.zhuangxv.bot.core.network.BotClient;
import com.zhuangxv.bot.event.message.QQConnectedEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

@Sharable
public class WsHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger log = LoggerFactory.getLogger(WsHandler.class);
    private final BotConfig botConfig;
    private final BotDispatcher botDispatcher;
    private final WsNetwork wsNetwork;
    private final Map<Long, Bot> bots;
    private boolean shutdown = false;
    private WebSocketClientHandshaker webSocketClientHandshaker;

    public WebSocketClientHandshaker getWebSocketClientHandshaker() {
        return this.webSocketClientHandshaker;
    }

    public void channelActive(ChannelHandlerContext ctx) throws URISyntaxException {
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + this.botConfig.getAccessToken());
        this.webSocketClientHandshaker = WebSocketClientHandshakerFactory.newHandshaker(new URI(this.botConfig.getUrl()), WebSocketVersion.V13, (String)null, false, httpHeaders, 104857600);
        this.webSocketClientHandshaker.handshake(ctx.channel());
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        for(Map.Entry<Long, Bot> botEntry : this.bots.entrySet()) {
            BotClient botClient = ((Bot)botEntry.getValue()).getBotClient();
            if (botClient instanceof WsBotClient && ((WsBotClient)botClient).getChannel().id().asLongText().equals(ctx.channel().id().asLongText())) {
                this.bots.remove(botEntry.getKey());
            }
        }

        if (!this.shutdown) {
            this.wsNetwork.connection();
        }

    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Channel ch = ctx.channel();
        if (!this.webSocketClientHandshaker.isHandshakeComplete() && msg instanceof FullHttpResponse) {
            try {
                this.webSocketClientHandshaker.finishHandshake(ch, (FullHttpResponse)msg);
                BotClient botClient = new WsBotClient(this.wsNetwork.getChannel());
                Bot bot = new Bot(this.botConfig, botClient);
                (new Thread(() -> {
                    try {
                        bot.flushBotInfo();
                        log.info(String.format("[%s]连接QQ服务器成功 QQ server connected!", bot.getBotName()));
                        if (bot.getBotId() == 0L || StringUtils.isEmpty(bot.getBotName())) {
                            log.error("ws正向连接失败.");
                            this.shutdown = true;
                            ctx.close();
                            return;
                        }

                        Map<String, BotContext> beans = BotFactory.getBeansByClass(BotContext.class);
                        if (beans != null) {
                            for(BotContext botContext : beans.values()) {
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
                        } catch (Exception var7x) {
                            log.error(var7x.getMessage(), var7x);
                        }

                        bot.getCompletableFuture().complete(1L);
                    } catch (Exception var8) {
                        this.shutdown = true;
                        ctx.close();
                        log.error(var8.getMessage(), var8);
                    }

                })).start();
            } catch (WebSocketHandshakeException var6) {
                log.info(var6.getMessage());
                log.error("连接QQ服务器失败,accessToken 配置错误! QQ server ws failed to connect, Token authentication failed!");
                BotFactory.getApplicationContext().close();
                Runtime.getRuntime().exit(0);
            }
        } else if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame)msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame)frame;
                this.botDispatcher.handle(textFrame.text());
            } else if (frame instanceof CloseWebSocketFrame) {
                ch.close();
            }
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

    public WsHandler(BotConfig botConfig, BotDispatcher botDispatcher, WsNetwork wsNetwork, Map<Long, Bot> bots) {
        this.botConfig = botConfig;
        this.botDispatcher = botDispatcher;
        this.wsNetwork = wsNetwork;
        this.bots = bots;
    }
}
