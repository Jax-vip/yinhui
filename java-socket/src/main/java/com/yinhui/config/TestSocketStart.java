package com.yinhui.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("testSocket")
public class TestSocketStart {


    public static ServerSocket testSocket = null;

    private static final ThreadPoolExecutor testSocketThreadPool = new ThreadPoolExecutor(15, 15,
            10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @PostConstruct
    public void start() {
        new Thread(() -> {
            try {
                testSocket = new ServerSocket(1234);
                log.info("socket服务端开启");
                while (true) {
                    log.info("等待新连接");
                    Socket socket = testSocket.accept();
                    log.info("获取到客户端地址:{}", socket.getRemoteSocketAddress());
                    testSocketThreadPool.execute(new TestSocketService(socket));
                }
            } catch (IOException e) {
                log.info("socket服务启动异常");
            }
        }, "testSocket").start();
    }

}
