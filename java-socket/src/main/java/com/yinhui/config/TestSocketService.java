package com.yinhui.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
public class TestSocketService implements Runnable{

    private Socket socket;

    @Override
    @SneakyThrows
    public void run() {
        byte[] bytes = new byte[1024];
        while (true){
            try{
                InputStream inputStream = socket.getInputStream();
                final boolean keepAlive = socket.getKeepAlive();
                log.info(String.valueOf(socket.getRemoteSocketAddress()));
                OutputStream oos = socket.getOutputStream();
                String str = "hello";
                Arrays.fill(bytes, (byte) 0);
                int read = inputStream.read(bytes);

                oos.write(str.getBytes());
                oos.flush();
            } catch (Exception e) {
                log.info("接收数据异常socket关闭");
                socket.close();
                e.printStackTrace();
            }
        }

    }
}
