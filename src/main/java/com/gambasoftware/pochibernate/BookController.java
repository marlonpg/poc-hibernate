package com.gambasoftware.pochibernate;

import com.gambasoftware.pochibernate.data.BookService;
import com.gambasoftware.pochibernate.data.entities.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.list();
    }
}
