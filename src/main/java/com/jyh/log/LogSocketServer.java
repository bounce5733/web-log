package com.jyh.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2020/2/6 20:20
 * @Version 1.0
 **/
@Component
@Slf4j
public class LogSocketServer {

    public LogSocketServer() {
        new Thread(() -> {
            log.info("启动日志接收器socket...");
            ServerSocket ss = null;
            try {
                ss = new ServerSocket(Const.SOCKET_PORT, 50, InetAddress.getByName(Const.SOCKET_IP));
                for (; ; ) {
                    Socket sock = ss.accept();
                    log.info("connected from " + sock.getRemoteSocketAddress());
                    Thread t = new Handler(sock);
                    t.start();
                }
            } catch (IOException e) {
                log.error("日志接收器socket启动失败，程序退出", e);
                System.exit(0);
            }
        }).start();
        log.info("日志接收器socket启动成功...");
    }
}

@Slf4j
class Handler extends Thread {

    private Socket sock;

    public Handler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try (InputStream input = this.sock.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            String line = null;
            while ((line = reader.readLine()) != null) {
                MyWebSocket.sendMessage(line);
            }
        } catch (Exception e) {
            try {
                this.sock.close();
            } catch (IOException ioe) {
            }
            log.info("Socket客户端断开连接");
        }
    }

}
