package com.example.demonetty.server;//package com.example.demonetty.server;
import com.example.demonetty.entity.T1;
import com.example.demonetty.service.T1Service;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private T1Service t1Service;

    private static final List<Channel> channels = new ArrayList<>();

    public ServerHandler(T1Service t1Service) {
        this.t1Service = t1Service;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Client " + ctx.channel().remoteAddress() + " connected");
        channels.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Client " + ctx.channel().remoteAddress() + " has disconnected");
        cause.printStackTrace();
        ctx.close();
        channels.remove(ctx.channel());
    }


    //       Handler received message in here
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Received message from client " + ctx.channel().remoteAddress() + ": " + msg);


        String tmp = msg;

//        You can uncomment this code to send message to Client using String

//        Scanner sc = new Scanner(System.in);
//        String messageToClient = sc.nextLine();
//        ByteBuf buf = Unpooled.copiedBuffer(messageToClient.getBytes());
//        System.out.println("Message sent to client: " + messageToClient);
//        ctx.channel().writeAndFlush(buf);


        // This is my String Handler and save to database
        
        try{
            String[] dataParts = msg.split("\\}\\{");
            for(String part : dataParts){
                part = part.replaceAll("^\\{|\\}$", "");
                String[] messageList = part.split(",");
                if (messageList.length < 10) {
                    throw new Exception("Khong phai T1");
                }

                int i = 0;
                T1 t1 = new T1();
                if(i < messageList.length){
                    t1.setDateTime(messageList[i++]);
                    t1.setTypeEquip(messageList[i++]);
                    t1.setVersion(messageList[i++]);
                    t1.setSerial(messageList[i++]);
                    t1.setType(messageList[i++]);
                    t1.setSimNum(messageList[i++]);
                    t1.setPhoneNum(messageList[i++]);
                    t1.setPassword(messageList[i++]);
                    t1.setReasonRestart(messageList[i++]);
                    t1.setSequence(messageList[i++]);
                    t1.setReceiveMessage(tmp);
                }
                t1Service.save(t1);
            }
        }
        catch (Exception e) {
        }
    }
}

