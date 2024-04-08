package com.example.demonetty;
import com.example.demonetty.client.Client;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        try {
            System.out.println("Creating new Client");
            Client client = new Client("127.0.0.1", 8888);
            ChannelFuture channelFuture = client.startup();
            System.out.println("New Client is created");
            readTxt(channelFuture, client);
//            readFile(channelFuture, client);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error occurred");
        }
    }
    public static void readTxt(ChannelFuture channelFuture, Client client){
        while (true){
            if (channelFuture.isSuccess()){
                Scanner sc = new Scanner(System.in);
                String message = sc.nextLine();
                Channel channel = client.getChannel();
                if (channel != null && channel.isActive()){
                    channel.writeAndFlush(Unpooled.wrappedBuffer(message.getBytes()))
                            .addListener((ChannelFutureListener) future -> {
                                if (future.isSuccess()) {
                                    System.out.println("Message sent to server: " + message);
                                } else {
                                    System.err.println("Message sending failed");
                                }
                            });
                }
                else{
                    System.err.println("Failed to establish connection with server.");
                }
            }
            else{
                System.err.println("Failed to establish connection with server.");
            }
        }
    }

    public static void readFile(ChannelFuture channelFuture, Client client) throws FileNotFoundException {
        if (channelFuture.isSuccess()) {
            Scanner sc = new Scanner(new File("D:\\Thuc tap\\netty - last\\demonetty\\src\\main\\java\\com\\example\\demonetty\\data.txt"));
            while (sc.hasNextLine()) {
                String message = sc.nextLine();
                Channel channel = client.getChannel();
                if (channel != null && channel.isActive()) {
                    channel.writeAndFlush(Unpooled.wrappedBuffer(message.getBytes()))
                            .addListener((ChannelFutureListener) future -> {
                                if (future.isSuccess()) {
                                    System.out.println("Message sent to server: " + message);
                                }
                                else {
                                    System.err.println("Message sending failed");
                                }
                            });
                }
                else {
                    System.err.println("Failed to establish connection with server.");
                }
            }
        }
    }
}
