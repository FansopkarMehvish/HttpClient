package com.example.HttpClientDemo.service;

import com.example.HttpClientDemo.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${external.api.base-url}")
    private String baseUrl;

    public BookService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Book> getAllBooks() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/getAllBooks"))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return Arrays.asList(objectMapper.readValue(httpResponse.body(), Book[].class));
    }

    public String createBook(Book book) throws Exception {
        String bookJson = objectMapper.writeValueAsString(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/addBook"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bookJson))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String updateBook(Long id, Book book) throws Exception {
        String bookJson = objectMapper.writeValueAsString(book);

        // Use query parameter instead of appending id to the path
        String url = String.format("%s/updateBook?id=%d", baseUrl, id); // Add 'id' as query parameter

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(bookJson))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String deleteBook(Long id) throws Exception {
        String url = String.format("%s/deleteBook?id=%d", baseUrl, id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
