package com.gambasoftware.pochibernate.api;

import com.gambasoftware.pochibernate.api.converters.AuthorConverter;
import com.gambasoftware.pochibernate.api.models.AuthorModel;
import com.gambasoftware.pochibernate.data.AuthorService;
import com.gambasoftware.pochibernate.data.entities.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    public AuthorModel create(@RequestBody Author author) {
        Author authorSaved = authorService.save(author);
        return AuthorConverter.convert(authorSaved);
    }

    @GetMapping("/authors")
    public ResponseEntity getAuthor() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors.stream()
                .map(AuthorConverter::convert)
                .collect(Collectors.toList()));
    }

    @GetMapping("/authors/{authorId}")
    public ResponseEntity getAuthor(@PathVariable Long authorId) {
        Author author = authorService.get(authorId);
        return ResponseEntity.ok(AuthorConverter.convert(author));
    }
}
