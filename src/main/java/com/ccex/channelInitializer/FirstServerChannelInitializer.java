package com.ccex.channelInitializer;

import com.ccex.handler.FirstServerHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class FirstServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    protected void initChannel(SocketChannel socketChannel){
        ChannelPipeline ch = socketChannel.pipeline();
        ch.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[] { 0x7e }),
                Unpooled.copiedBuffer(new byte[] { 0x7e, 0x7e })));
        ch.addLast(new FirstServerHandler());
    }
}

