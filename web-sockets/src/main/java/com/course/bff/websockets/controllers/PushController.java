package com.course.bff.websockets.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/websocket")
@RestController
public class PushController {
    private SimpMessagingTemplate template;
    public PushController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @PostMapping
    @MessageMapping("/push")
    @SendTo("/topic/messages")
    public String send(@RequestBody String message) {
        this.template.convertAndSend("/topic/messages", String.format("%s", message));
        return String.format("%s", message);
    }
}
