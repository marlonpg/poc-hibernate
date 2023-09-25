package com.gambasoftware.pochibernate.data.repositories;

import com.gambasoftware.pochibernate.data.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
