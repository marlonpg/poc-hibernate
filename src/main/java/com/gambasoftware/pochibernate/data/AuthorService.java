package com.gambasoftware.pochibernate.data;

import com.gambasoftware.pochibernate.data.entities.Author;
import com.gambasoftware.pochibernate.data.repositories.AuthorRepository;
import com.gambasoftware.pochibernate.observability.MetricsService;
import net.sf.ehcache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private MetricsService metricsService;

    public AuthorService(AuthorRepository authorRepository, MetricsService metricsService) {
        this.authorRepository = authorRepository;
        this.metricsService = metricsService;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author get(Long authorId){
        long startTime = System.nanoTime();
        Author author = authorRepository.findById(authorId)
                .orElse(null);
        metricsService.saveMetric(AuthorService.class.getSimpleName(), ".get", new AtomicLong(System.nanoTime() - startTime));
        return author;
    }

    public Author save(Author author) {
        long startTime = System.nanoTime();
        Author authorSaved = authorRepository.save(author);
        metricsService.saveMetric(AuthorService.class.getSimpleName(), ".save", new AtomicLong(System.nanoTime() - startTime));
        return authorSaved;
    }
}
