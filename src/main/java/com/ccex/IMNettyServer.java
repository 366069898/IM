package com.ccex;

import com.ccex.channelInitializer.FirstServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class IMNettyServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new FirstServerChannelInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8998).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
