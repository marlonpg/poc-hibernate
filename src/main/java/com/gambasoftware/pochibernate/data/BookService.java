package com.gambasoftware.pochibernate.data;

import com.gambasoftware.pochibernate.data.entities.Author;
import com.gambasoftware.pochibernate.data.entities.Book;
import com.gambasoftware.pochibernate.data.repositories.BookRepository;
import com.gambasoftware.pochibernate.observability.MetricsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private BookRepository bookRepository;
    private AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> list() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book save(Book book) {
        for (Author a : book.getAuthors()) {
            Author author = authorService.get(a.getAuthorId());
            if (author != null) {
                book.getAuthors().add(author);
            }
        }

        long startTime = System.currentTimeMillis();
        Book bookSaved = bookRepository.save(book);
        MetricsService.saveMetric(BookService.class.getSimpleName() + ".save", new AtomicLong(System.currentTimeMillis() - startTime));
        return bookSaved;
    }

    public Book get(Long bookId) {
        long startTime = System.currentTimeMillis();
        Book book = bookRepository.findById(bookId).orElse(null);
        MetricsService.saveMetric(BookService.class.getSimpleName() + ".get", new AtomicLong(System.currentTimeMillis() - startTime));
        return book;
    }
}
