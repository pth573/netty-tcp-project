package com.example.demonetty.server;

import static org.mockito.Mockito.*;
import com.example.demonetty.entity.T1;
import com.example.demonetty.service.T1Service;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ServerHandlerTest {
    private T1Service t1Service;
    private ServerHandler serverHandler;
    private ChannelHandlerContext ctx;
    private EmbeddedChannel channel;

    @BeforeEach
    public void setUp() {
        t1Service = mock(T1Service.class);
        serverHandler = new ServerHandler(t1Service);
        ctx = mock(ChannelHandlerContext.class);
        channel = new EmbeddedChannel(serverHandler);
        when(ctx.channel()).thenReturn(channel);
    }


    @Test
    public void testChannelRead0_InvalidMessageFormat() throws Exception {
        String invalidMessage = "aaaaa";
        try {
            serverHandler.channelRead0(ctx, invalidMessage);
        } catch (Exception e) {
        }
        verify(t1Service, never()).save(any(T1.class));
    }


    @Test
    public void testChannelRead0() throws Exception {
        String filePath = "D:\\Thuc tap\\netty_project_last\\demonetty\\src\\main\\resources\\data.txt";

        try (Scanner sc = new Scanner(new File(filePath))) {
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                try {
                    serverHandler.channelRead0(ctx, line);
                } catch (Exception e) {
                    System.out.println("Khong phai T1 khong luu");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        verify(t1Service, atLeastOnce()).save(any(T1.class));
    }


    @Test
    public void testChannelRead0_ValidMessageFormat() throws Exception {
        String message = "{2009-12-16 10:00:00,1,W4,100000001,T1,452040666805809,******,123456,0,129}";
        serverHandler.channelRead0(ctx, message);
        verify(t1Service, times(1)).save(any(T1.class));
    }
}
