package com.course.bff.mobileapp.handler;

import com.course.bff.mobileapp.client.ApiClient;
import com.course.bff.mobileapp.item.AuthorItem;
import com.course.bff.mobileapp.item.BookItem;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void handleFrame(StompHeaders headers, Object payload) {
        Map<String, Object> result = apiClient.getDetails();
        List<BookItem> books = objectMapper.convertValue(result.get("books"), new TypeReference<List<BookItem>>() {
        });
        List<AuthorItem> authors = objectMapper.convertValue(result.get("authors"), new TypeReference<List<AuthorItem>>() {
        });

        books.forEach(book -> {
            Optional<String> authorName = authors.stream()
                    .filter(author -> author.getId().equals(book.getAuthorId()))
                    .map(AuthorItem::getLastName)
                    .findFirst();
            log.info("Book: {}. Author: {}", book.getTitle(), authorName.orElse(null));
        });
    }
}
