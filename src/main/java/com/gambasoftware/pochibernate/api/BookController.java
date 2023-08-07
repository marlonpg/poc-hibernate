package com.gambasoftware.pochibernate.api;

import com.gambasoftware.pochibernate.api.converters.BookConverter;
import com.gambasoftware.pochibernate.data.BookService;
import com.gambasoftware.pochibernate.data.entities.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity getBook(@PathVariable("bookId") Long bookId) {
        Book book = bookService.get(bookId);
        return ResponseEntity.ok(BookConverter.convert(book));

    }

    @PostMapping
    public ResponseEntity save(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.save(book));
    }
}
