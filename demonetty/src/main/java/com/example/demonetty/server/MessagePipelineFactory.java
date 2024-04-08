package com.example.demonetty.server;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.stereotype.Component;


@Component
public class MessagePipelineFactory implements PipelineFactory {
    private final int availableProcessors;
    private final EventExecutorGroup executors;

    public MessagePipelineFactory() {
        availableProcessors = Runtime.getRuntime().availableProcessors();
        executors = new DefaultEventExecutorGroup(availableProcessors);
    }

    @Override
    public ChannelInitializer<SocketChannel> createInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                final MessageDecoder decoder = new MessageDecoder();
                pipeline.addLast("decoder", decoder);

                final MessageHandler handler = new MessageHandler();
                pipeline.addLast(executors, "handler", handler);
            }
        };
    }
}
