package com.course.bff.books.services;

import com.course.bff.books.models.Book;
import com.course.bff.books.requests.CreateBookCommand;
import com.course.bff.books.responses.AuthorResponse;
import com.google.gson.Gson;
import org.asynchttpclient.*;
import org.asynchttpclient.util.HttpConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class BookService {
    private final ArrayList<Book> books;
    @Value("${authors.url}")
    private String authorsUrl;

    public BookService() {
        books = new ArrayList<>();
    }

    public Collection<Book> getBooks() {
        return this.books;
    }

    public Optional<Book> findById(UUID id) {
        return this.books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book create(CreateBookCommand createBookCommand) {
        Optional<AuthorResponse> authorSearch = getAuthor(createBookCommand.getAuthorId());
        if (authorSearch.isEmpty()) {
            throw new RuntimeException("Author isn't found");
        }

        Book book = new Book(UUID.randomUUID())
                .withTitle(createBookCommand.getTitle())
                .withAuthorId(authorSearch.get().getId())
                .withPages(createBookCommand.getPages());

        this.books.add(book);
        return book;
    }

    private Optional<AuthorResponse> getAuthor(UUID authorId) {
        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config().setConnectTimeout(500);
        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);
        Request socketRequest = new RequestBuilder(HttpConstants.Methods.GET)
                .setUrl(authorsUrl + authorId.toString())
                .build();

        ListenableFuture<Response> socketFuture = client.executeRequest(socketRequest);
        try {
            Response response = socketFuture.get();
            if (response.getStatusCode() != HttpStatus.OK.value()) {
                return Optional.empty();
            }

            AuthorResponse authorResponse = new Gson().fromJson(response.getResponseBody(), AuthorResponse.class);
            return Optional.of(authorResponse);
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }
}
