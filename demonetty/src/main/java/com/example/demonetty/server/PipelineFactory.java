package com.example.demonetty.server;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public interface PipelineFactory {
    ChannelInitializer<SocketChannel> createInitializer();
}
