package com.gambasoftware.pochibernate.data;

import com.gambasoftware.pochibernate.data.entities.Author;
import com.gambasoftware.pochibernate.data.repositories.AuthorRepository;
import com.gambasoftware.pochibernate.observability.MetricsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author get(Long authorId){
        long startTime = System.currentTimeMillis();
        Author author = authorRepository.findById(authorId)
                .orElse(null);
        MetricsService.saveMetric(AuthorService.class.getSimpleName() + ".get", new AtomicLong(System.currentTimeMillis() - startTime));
        return author;
    }

    public Author save(Author author) {
        long startTime = System.currentTimeMillis();
        Author authorSaved = authorRepository.save(author);
        MetricsService.saveMetric(AuthorService.class.getSimpleName() + ".save", new AtomicLong(System.currentTimeMillis() - startTime));
        return authorSaved;
    }
}
