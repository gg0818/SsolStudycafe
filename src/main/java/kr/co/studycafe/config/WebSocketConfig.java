package kr.co.studycafe.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import kr.co.studycafe.handler.ChatHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler = new ChatHandler();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}