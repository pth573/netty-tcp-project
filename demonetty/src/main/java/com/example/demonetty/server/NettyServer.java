package com.example.demonetty.server;
import com.example.demonetty.service.T1Service;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {
    private final EventLoopGroup bossLoopGroup;
    private final EventLoopGroup workerLoopGroup;
    private final ChannelGroup channelGroup;
    private final Class<? extends PipelineFactory> pipelineFactoryClass;
    private final EventExecutorGroup executorGroup;
    private final T1Service t1Service;

    @Autowired
    public NettyServer(T1Service t1Service) {
        this.bossLoopGroup = new NioEventLoopGroup();
        this.workerLoopGroup = new NioEventLoopGroup();
        this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.pipelineFactoryClass = MessagePipelineFactory.class;
        this.executorGroup = new DefaultEventExecutorGroup(4);
        this.t1Service = t1Service;
    }

    public final void startup(int port) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossLoopGroup, workerLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.AUTO_CLOSE, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ServerChannelInitializer(t1Service));

        try {
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            shutdown();
            throw e;
        }
    }

    public final void shutdown() throws Exception {
        bossLoopGroup.shutdownGracefully();
        workerLoopGroup.shutdownGracefully();
        executorGroup.shutdownGracefully();
    }
}
