package com.jyh.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2020/2/4 20:02
 * @Version 1.0
 **/
public class LogAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private Layout<ILoggingEvent> layout;

    private static Socket logSocket = null;

    @Override
    public void start() {
        if (null == layout) {
            addWarn("Layout was not defined");
        } else {
            System.out.println("启动WebLogger...");
            super.start();
            System.out.println("WebLogger启动成功...");
        }

    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (null == eventObject || !this.isStarted()) {
            return;
        }
        // 此处自定义输入
        // 获取输出值：event.getFormattedMessage()
        // 格式化输出
        String log = layout.doLayout(eventObject);
        System.out.println("自定义日志输出：" + log);
        sendLog(log);
    }

    @Override
    public void stop() {
        System.out.println("logback-stop方法被调用");
        if (!this.isStarted()) {
            super.stop();
        }
        if (logSocket != null && !logSocket.isClosed()) {
            try {
                logSocket.close();
            } catch (IOException e) {
                System.out.println("关闭WebLogger Socket客户端异常");
                e.printStackTrace();
            }
        }
    }

    private static void sendLog(String msg) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream(), StandardCharsets.UTF_8));
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            System.out.println("WebLogger Socket客户端发送日志异常");
            e.printStackTrace();
        }
    }

    private static Socket getSocket() {
        if (logSocket == null) {
            System.out.println("启动WebLogger Socket客户端");
            try {
                logSocket = new Socket(Const.SOCKET_IP, Const.SOCKET_PORT);
            } catch (IOException e) {
                System.out.println("WebLogger Socket客户端启动失败！");
                e.printStackTrace();
            }
        }
        return logSocket;
    }

    public Layout<ILoggingEvent> getLayout() {
        return layout;
    }

    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }
}
