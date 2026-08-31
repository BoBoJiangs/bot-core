/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.bootstrap.Bootstrap
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelInitializer
 *  io.netty.channel.ChannelOption
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.nio.NioEventLoopGroup
 *  io.netty.channel.socket.SocketChannel
 *  io.netty.channel.socket.nio.NioSocketChannel
 *  io.netty.handler.codec.http.HttpClientCodec
 *  io.netty.handler.codec.http.HttpObjectAggregator
 *  io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.zhuangxv.bot.core.network.ws;

import com.zhuangxv.bot.config.BotConfig;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.component.BotDispatcher;
import com.zhuangxv.bot.core.network.BotNetwork;
import com.zhuangxv.bot.core.network.ws.WsHandler;
import com.zhuangxv.bot.exception.BotException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsNetwork
implements BotNetwork {
    private static final Logger log = LoggerFactory.getLogger(WsNetwork.class);
    private final Bootstrap clientBootstrap = new Bootstrap();
    private BotConfig botConfig;
    private Channel channel;
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 10;

    @Override
    public void init(final BotConfig botConfig, final Map<Long, Bot> bots, final BotDispatcher botDispatcher) {
        this.botConfig = botConfig;
        final WsNetwork instance = this;
        ((Bootstrap)((Bootstrap)((Bootstrap)this.clientBootstrap.group((EventLoopGroup)new NioEventLoopGroup())).channel(NioSocketChannel.class)).option(ChannelOption.SO_KEEPALIVE, true)).handler((ChannelHandler)new ChannelInitializer<SocketChannel>(){

            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast(new ChannelHandler[]{new HttpClientCodec()}).addLast(new ChannelHandler[]{new HttpObjectAggregator(0x6400000)}).addLast(new ChannelHandler[]{new WebSocketFrameAggregator(0x6400000)}).addLast(new ChannelHandler[]{new WsHandler(botConfig, botDispatcher, instance, bots)});
            }
        });
        this.connection();
    }

    protected Channel getChannel() {
        if (this.channel != null && this.channel.isActive() && ((WsHandler)this.channel.pipeline().get(WsHandler.class)).getWebSocketClientHandshaker().isHandshakeComplete()) {
            return this.channel;
        }
        throw new BotException(String.format("[%s]\u8fde\u63a5\u5931\u8d25", this.botConfig.getUrl()));
    }

    public void connection() {
        if (this.channel == null || !this.channel.isActive()) {
            URI wsUri;
            try {
                wsUri = new URI(this.botConfig.getUrl());
            }
            catch (URISyntaxException var3) {
                throw new BotException("websocket url \u683c\u5f0f\u9519\u8bef.");
            }
            ChannelFuture channelFuture = this.clientBootstrap.connect(wsUri.getHost(), wsUri.getPort());
            channelFuture.addListener((GenericFutureListener)new GenericFutureListener<ChannelFuture>(){

                public void operationComplete(ChannelFuture futureListener) throws Exception {
                    if (futureListener.isSuccess()) {
                        WsNetwork.this.channel = futureListener.channel();
                        WsNetwork.this.retryCount = 0;
                    } else if (WsNetwork.this.retryCount < 10) {
                        WsNetwork.this.retryCount++;
                        log.error("\u8fde\u63a5QQ\u670d\u52a1\u5668\u5931\u8d25\uff0c20\u79d2\u540e\u91cd\u8bd5... (\u91cd\u8bd5\u6b21\u6570: {}/{})", (Object)WsNetwork.this.retryCount, (Object)10);
                        log.error("\u5931\u8d25\u5730\u5740: {},Failed to connect to QQ server used ws, try connect after 10s,Failed url: {}", (Object)WsNetwork.this.botConfig.getUrl(), (Object)WsNetwork.this.botConfig.getUrl());
                        futureListener.channel().eventLoop().schedule(WsNetwork.this::connection, 20L, TimeUnit.SECONDS);
                    } else {
                        log.error("\u5df2\u8fbe\u5230\u6700\u5927\u91cd\u8bd5\u6b21\u6570({})\uff0c\u505c\u6b62\u91cd\u8bd5", (Object)10);
                    }
                }
            });
        }
    }
}

