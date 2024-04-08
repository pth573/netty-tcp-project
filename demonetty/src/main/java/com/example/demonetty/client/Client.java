package com.example.demonetty.client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Client {
    private final String host;
    private final int port;
    private Channel channel;
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private final Bootstrap bootstrap;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // Lập lịch cho việc tái kết nối
    private final int reconnectDelaySeconds = 1;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.bootstrap = initializeBootstrap();
    }

    public ChannelFuture startup() {
        try {
            return connect();
        } catch (Exception e) {
            System.err.println("Startup error: " + e.getMessage());
            return null;
        }
    }

    public void shutdown() {
        workGroup.shutdownGracefully();
        scheduler.shutdown();
    }

    public Channel getChannel() {
        return this.channel;
    }

    private Bootstrap initializeBootstrap() {
        Bootstrap b = new Bootstrap();
        b.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyHandler(Client.this));
                    }
                });
        return b;
    }

    private ChannelFuture connect() throws InterruptedException {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            this.channel = future.channel();
            System.out.println("Connected to server");
            return future;
        } catch (Exception e) {
            System.err.println("Connection interrupted: " + e.getMessage());
            System.err.println("Connection failed: " + e.getMessage());
            scheduleReconnect();
        }
        return null;
    }

    public void scheduleReconnect() {
        scheduler.schedule(this::connect, reconnectDelaySeconds, TimeUnit.SECONDS);
    }
}
