package com.gambasoftware.pochibernate.data;

import com.gambasoftware.pochibernate.data.entities.Book;
import com.gambasoftware.pochibernate.data.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> list() {
        return bookRepository.findAll();
    }
}
