package com.gambasoftware.pochibernate.api;

import com.gambasoftware.pochibernate.api.converters.BookConverter;
import com.gambasoftware.pochibernate.data.BookService;
import com.gambasoftware.pochibernate.data.entities.Author;
import com.gambasoftware.pochibernate.data.entities.Book;
import com.gambasoftware.pochibernate.observability.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MetricsService metricsService;


    @GetMapping("/books/{bookId}")
    public ResponseEntity getBook(@PathVariable("bookId") String bookId) {
        Book book = bookService.get(bookId);
        return ResponseEntity.ok(BookConverter.convert(book));

    }

    @PostMapping
    public ResponseEntity save(@RequestBody Book book) {
        return ResponseEntity.ok(BookConverter.convert(bookService.save(book)));
    }

    @PostMapping("/books/batchSize/{size}/scenario/{scenario}")
    @Transactional
    public ResponseEntity batchSave(@PathVariable("size") Long size, @PathVariable("scenario") String scenario) {
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            logger.info("Creating book= " + i);
            Book book = new Book();
            //book.setId(UUID.randomUUID().toString());
            book.setName("Book name "+ i + scenario);
            Set<Author> authors = new HashSet<>();
            Author author = new Author();
            author.setAuthorId(1L);
            authors.add(author);
            book.setAuthors(authors);
            bookService.save(book, scenario);
            if (i > 0 && i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        metricsService.saveMetric("total-time-batch-book", ".save", new AtomicLong(System.nanoTime() - startTime), scenario);
        //metricsService.saveMetric("total-time-batch-book", ".save", new AtomicLong(System.nanoTime() - startTime), scenario);

        return ResponseEntity.ok("ok");
    }
}
