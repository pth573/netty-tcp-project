package com.example.demonetty.server;
import com.example.demonetty.entity.Message;
import io.netty.channel.*;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        handleMessage(ctx, msg);
    }

    private void handleMessage(ChannelHandlerContext ctx, Message msg) {
    }
}