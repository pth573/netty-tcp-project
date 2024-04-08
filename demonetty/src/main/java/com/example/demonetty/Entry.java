package com.example.demonetty;
import com.example.demonetty.server.NettyServer;
import com.example.demonetty.service.T1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Entry {

    @Autowired
    private T1Service t1Service;

    public static void main(String[] args) {
        SpringApplication.run(Entry.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
        return new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
                System.out.println("Main");
                try {
                    ExecutorService executorService = Executors.newFixedThreadPool(5);
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("Server in port 8888 ...");
                                new NettyServer(t1Service).startup(8888);
                                System.out.println("TCP server in port 8888 ...");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
