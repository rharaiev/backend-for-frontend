package com.course.bff.clientapp.client;

import com.course.bff.clientapp.item.AuthorItem;
import com.course.bff.clientapp.item.BookItem;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiClient {
    private static final String AUTHOR_URL = "http://bff-authors-service:8080/api/v1/authors/";
    private static final String BOOK_URL = "http://bff-books-service:8080/api/v1/books/";
    private static final String AUTH_TOKEN = "abc";

    RestTemplate restTemplate;
    HttpHeaders headers;

    public ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, AUTH_TOKEN);
    }

    public AuthorItem getAuthor(UUID authorId) throws URISyntaxException {
        return restTemplate.exchange(new URI(AUTHOR_URL + authorId), HttpMethod.GET, new HttpEntity<>(headers), AuthorItem.class).getBody();
    }

    public BookItem getBook(UUID bookId) throws URISyntaxException {
        return restTemplate.exchange(new URI(BOOK_URL + bookId), HttpMethod.GET, new HttpEntity<>(headers), BookItem.class).getBody();
    }
}
