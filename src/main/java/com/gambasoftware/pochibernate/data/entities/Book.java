package com.gambasoftware.pochibernate.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book {
    @Id
    //analisar os tipos de GenerationType e GenericGenerator
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

//    @GeneratedValue(generator = "sequence-generator")
//    @GenericGenerator(
//            name = "sequence-generator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "book_sequence"),
//                    @Parameter(name = "initial_value", value = "4"),
//                    @Parameter(name = "increment_size", value = "1")
//            }
//    )
    private Long id;
    private String name;
    @ManyToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authorId")})
    private Set<Author> authors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
