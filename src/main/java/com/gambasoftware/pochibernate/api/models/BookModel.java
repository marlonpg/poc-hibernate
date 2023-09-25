package com.gambasoftware.pochibernate.api.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BookModel {
    private Long id;
    private String name;
    private Set<AuthorModel> authors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<AuthorModel> getAuthors() {
        return authors;
    }

    public static final class BookModelBuilder {
        private Long id;
        private String name;
        private Set<AuthorModel> authors;

        private BookModelBuilder() {
        }

        public static BookModelBuilder aBookModel() {
            return new BookModelBuilder();
        }

        public BookModelBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public BookModelBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BookModelBuilder withAuthors(Set<AuthorModel> authors) {
            this.authors = authors;
            return this;
        }

        public BookModel build() {
            BookModel bookModel = new BookModel();
            bookModel.name = this.name;
            bookModel.authors = this.authors;
            bookModel.id = this.id;
            return bookModel;
        }
    }
}
