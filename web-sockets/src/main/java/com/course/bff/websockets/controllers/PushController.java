package com.course.bff.websockets.controllers;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class PushController implements MessageListener {
    private final SimpMessagingTemplate template;

    public PushController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        template.convertAndSend("/topic/messages", String.format("%s", message));
    }
}
