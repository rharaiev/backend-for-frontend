package com.course.bff.mobilefrontend.controller;

import com.course.bff.mobilefrontend.client.ApiClient;
import com.course.bff.mobilefrontend.item.AuthorItem;
import com.course.bff.mobilefrontend.item.BookItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/details")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AggregationController {

    ApiClient apiClient;
    ObjectMapper objectMapper;

    @GetMapping
    public Map<String, Object> getAuthorsAndBooks() {
        List<AuthorItem> authors = objectMapper.convertValue(apiClient.getAuthors(), new TypeReference<List<AuthorItem>>() {
        });
        List<BookItem> books = objectMapper.convertValue(apiClient.getBooks(), new TypeReference<List<BookItem>>() {
        });

        Map<String, Object> result = new HashMap<>();
        result.put("authors", authors.stream().map(AuthorItem::getLastName).collect(Collectors.toList()));
        result.put("books", books.stream().map(BookItem::getTitle).collect(Collectors.toList()));

        return result;
    }

}
