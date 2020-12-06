package com.course.bff.clientapp.handler;

import com.course.bff.clientapp.item.AuthorItem;
import com.course.bff.clientapp.client.ApiClient;
import com.course.bff.clientapp.item.BookItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionHandler extends StompSessionHandlerAdapter {

    String topic;
    ObjectMapper objectMapper;
    ApiClient apiClient;

    @Override
    public void afterConnected(final StompSession session, final StompHeaders connectedHeaders) {
        log.info("New session established: {}", session.getSessionId());
        session.subscribe(topic, this);
        log.info("Subscribed to {}", topic);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Got an exception", exception);
    }

    @SneakyThrows
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        BookItem book = objectMapper.readValue((String) payload, BookItem.class);
        AuthorItem author = apiClient.getAuthor(book.getAuthorId());

        log.info("Book: {}. Author: {}", book, author);
    }
}
