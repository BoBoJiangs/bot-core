//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.core.network.wsr;

import com.zhuangxv.bot.config.BotConfig;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.component.BotDispatcher;
import com.zhuangxv.bot.core.network.BotNetwork;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsrNetwork implements BotNetwork {
    private static final Logger log = LoggerFactory.getLogger(WsrNetwork.class);

    public void init(final BotConfig botConfig, final Map<Long, Bot> bots, final BotDispatcher botDispatcher) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ((ServerBootstrap)bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new ChannelHandler[]{new HttpServerCodec()}).addLast(new ChannelHandler[]{new HttpObjectAggregator(104857600)}).addLast(new ChannelHandler[]{new WebSocketFrameAggregator(104857600)}).addLast(new ChannelHandler[]{new WsrHandler(botConfig, botDispatcher, bots)});
                }
            });
            URI uri = new URI(botConfig.getUrl());
            Channel ch = bootstrap.bind(new InetSocketAddress(uri.getHost(), uri.getPort())).sync().channel();
            log.info("ws反向服务器已开启,端口{},等待连接.", uri.getPort());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
