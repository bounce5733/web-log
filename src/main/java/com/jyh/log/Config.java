package com.jyh.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2020/2/6 17:06
 * @Version 1.0
 **/
@Configuration

public class Config {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
