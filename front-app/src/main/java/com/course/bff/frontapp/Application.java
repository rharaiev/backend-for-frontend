package com.course.bff.frontapp;

import com.course.bff.frontapp.client.ApiClient;
import com.course.bff.frontapp.handler.SessionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final String topic = "/topic/messages";
    private static final String URL = "ws://bff-web-sockets-service:8080/push";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new StringMessageConverter());

        ApiClient apiClient = new ApiClient(new RestTemplate());

        StompSessionHandler sessionHandler = new SessionHandler(topic, new ObjectMapper(), apiClient);
        stompClient.connect(URL, sessionHandler);
    }
}
