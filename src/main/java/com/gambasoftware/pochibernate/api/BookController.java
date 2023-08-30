package com.gambasoftware.pochibernate.api;

import com.gambasoftware.pochibernate.api.converters.BookConverter;
import com.gambasoftware.pochibernate.data.BookService;
import com.gambasoftware.pochibernate.data.entities.Author;
import com.gambasoftware.pochibernate.data.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);

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
        return ResponseEntity.ok(BookConverter.convert(bookService.save(book)));
    }

    @PostMapping("/books/batchSize/{size}/scenario/{scenario}")
    public ResponseEntity batchSave(@PathVariable("size") Long size, @PathVariable("scenario") String scenario) {
        for (int i = 0; i < size; i++) {
            logger.info("Creating book= " + i);
            Book book = new Book();
            book.setName("Book name "+ i + scenario);
            Set<Author> authors = new HashSet<>();
            Author author = new Author();
            author.setAuthorId(4166L);
            authors.add(author);
            book.setAuthors(authors);
            bookService.save(book);
        }
        return ResponseEntity.ok("ok");
    }
}
