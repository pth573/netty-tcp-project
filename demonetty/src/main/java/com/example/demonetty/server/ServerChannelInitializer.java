package com.example.demonetty.server;
import com.example.demonetty.service.T1Service;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final EventExecutorGroup executorGroup = new DefaultEventExecutorGroup(4);
    private final T1Service t1Service;
    @Autowired
    public ServerChannelInitializer(T1Service t1Service) {
        this.t1Service = t1Service;
    }
    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline()
                .addLast(new StringEncoder())
                .addLast(new StringDecoder())
                .addLast(executorGroup, new ServerHandler(t1Service));
    }
}
