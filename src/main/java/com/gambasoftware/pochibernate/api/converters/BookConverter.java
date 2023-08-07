package com.gambasoftware.pochibernate.api.converters;

import com.gambasoftware.pochibernate.api.models.BookModel;
import com.gambasoftware.pochibernate.data.entities.Book;

import java.util.HashSet;
import java.util.Set;

public class BookConverter {
    public static BookModel convert(Book book) {
        return BookModel.BookModelBuilder.aBookModel()
                .withId(book.getBookId())
                .withName(book.getName())
                .withAuthors(AuthorConverter.convert(book.getAuthors()))
                .build();
    }

    public static Set<BookModel> convert(Set<Book> books) {
        Set<BookModel> bookModels = new HashSet<>();
        for (Book book : books) {
            bookModels.add(BookModel.BookModelBuilder.aBookModel()
                    .withId(book.getBookId())
                    .withName(book.getName())
                    .build());
        }
        return bookModels;
    }
}
