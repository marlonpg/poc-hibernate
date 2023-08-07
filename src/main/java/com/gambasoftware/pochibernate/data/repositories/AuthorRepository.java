package com.gambasoftware.pochibernate.data.repositories;

import com.gambasoftware.pochibernate.data.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
