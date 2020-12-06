package com.course.bff.frontend.controller;

import com.course.bff.frontend.client.ApiClient;
import com.course.bff.frontend.item.AuthorItem;
import com.course.bff.frontend.item.BookItem;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/details")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AggregationController {

    ApiClient apiClient;

    @GetMapping
    public Map<String, Object> getAuthorsAndBooks() {
        List<AuthorItem> authors = apiClient.getAuthors();
        List<BookItem> books = apiClient.getBooks();

        Map<String, Object> result = new HashMap<>();
        result.put("authors", authors);
        result.put("books", books);

        return result;
    }

}
