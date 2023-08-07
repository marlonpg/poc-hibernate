package com.gambasoftware.pochibernate.api.models;

import java.util.HashSet;
import java.util.Set;

public class AuthorModel {
    private Long id;
    private String name;
    private Set<BookModel> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<BookModel> getBooks() {
        return books;
    }

    public static final class AuthorModelBuilder {
        private Long id;
        private String name;
        private Set<BookModel> books;

        private AuthorModelBuilder() {
        }

        public static AuthorModelBuilder anAuthorModel() {
            return new AuthorModelBuilder();
        }

        public AuthorModelBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AuthorModelBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AuthorModelBuilder withBooks(Set<BookModel> books) {
            this.books = books;
            return this;
        }

        public AuthorModel build() {
            AuthorModel authorModel = new AuthorModel();
            authorModel.books = this.books;
            authorModel.name = this.name;
            authorModel.id = this.id;
            return authorModel;
        }
    }
}
