package com.jyh.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2020/2/6 16:41
 * @Version 1.0
 **/
@ServerEndpoint(value = "/websocket")
@Controller
@Slf4j
public class MyWebSocket {

    public static final CopyOnWriteArrayList<Session> SESSIONS = new CopyOnWriteArrayList<>();

    /**
     * 数据发送
     *
     * @param message
     * @throws IOException
     */
    public static void sendMessage(String message) throws IOException {
        for (Session session : SESSIONS) {
            session.getBasicRemote().sendText(message);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        MyWebSocket.SESSIONS.add(session);
        log.info("有新的连接ID[" + session.getId() + "]加入，当前在线连接数为：" + SESSIONS.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端ID[" + session.getId() + "]的连接，消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("连接ID[" + session.getId() + "]发生异常！", error);
    }

    @OnClose
    public void onClose(Session session) {
        try {
            session.close();
            SESSIONS.remove(session);
            log.info("连接ID[" + session.getId() + "]关闭，当前在线连接数为：" + SESSIONS.size());
        } catch (IOException e) {
            log.error("关闭连接出错！", e);
        }
    }

}
