package com.heeexy.example.WebSocketServer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
@Configuration
@ConditionalOnWebApplication
public class WebSocketConfig {
    /*
    * 注入ServerEndpointExporter，
    * 自动注册并使用@ServerEndpoint
    * 注解声明的websocket endpoint
    * */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
