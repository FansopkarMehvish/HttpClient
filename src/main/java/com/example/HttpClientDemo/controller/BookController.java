package com.example.HttpClientDemo.controller;

import com.example.HttpClientDemo.model.Book;
import com.example.HttpClientDemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Create a new book
    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        try {
            String response = bookService.createBook(book);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create book: " + e.getMessage());
        }
    }

    // Update an existing book by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            String response = bookService.updateBook(id, book);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update book: " + e.getMessage());
        }
    }

    // Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            String response = bookService.deleteBook(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete book: " + e.getMessage());
        }
    }
}
