package com.course.bff.mobilefrontend.client;

import com.course.bff.mobilefrontend.item.AuthorItem;
import com.course.bff.mobilefrontend.item.BookItem;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiClient {

    String authorUrl;
    String bookUrl;
    RestTemplate restTemplate;

    @Autowired
    public ApiClient(@Value("${author.service.endpoint}") String authorUrl,
                     @Value("${book.service.endpoint}") String bookUrl,
                     RestTemplate restTemplate) {
        this.authorUrl = authorUrl;
        this.bookUrl = bookUrl;
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<AuthorItem> getAuthors() {
        return restTemplate.getForObject(authorUrl, List.class);
    }

    @SuppressWarnings("unchecked")
    public List<BookItem> getBooks() {
        return restTemplate.getForObject(bookUrl, List.class);
    }
}
