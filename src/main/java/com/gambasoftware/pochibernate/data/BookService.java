package com.gambasoftware.pochibernate.data;

import com.gambasoftware.pochibernate.data.entities.Author;
import com.gambasoftware.pochibernate.data.entities.Book;
import com.gambasoftware.pochibernate.data.repositories.BookRepository;
import com.gambasoftware.pochibernate.observability.MetricsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private BookRepository bookRepository;
    private AuthorService authorService;
    private MetricsService metricsService;

    public BookService(BookRepository bookRepository, AuthorService authorService, MetricsService metricsService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.metricsService = metricsService;
    }

    public List<Book> list() {
        return bookRepository.findAll();
    }

    //@Transactional
    public Book save(Book book) {
        Set<Author> authors = book.getAuthors();
        book.setAuthors(new HashSet<>());
        for (Author a : authors) {
            Author author = authorService.get(a.getAuthorId());
            if (author != null) {
                book.getAuthors().add(author);
            }
        }

        long startTime = System.nanoTime();
        Book bookSaved = bookRepository.save(book);
        metricsService.saveMetric(BookService.class.getSimpleName(), ".save", new AtomicLong(System.nanoTime() - startTime));
        return bookSaved;
    }

    public Book get(Long bookId) {
        long startTime = System.nanoTime();
        Book book = bookRepository.findById(bookId).orElse(null);
        metricsService.saveMetric(BookService.class.getSimpleName(), ".get", new AtomicLong(System.nanoTime() - startTime));
        return book;
    }
}
