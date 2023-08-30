package com.gambasoftware.pochibernate;

import com.gambasoftware.pochibernate.data.entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("batchinserts")
public class TestBatch {

    @PersistenceContext
    private EntityManager entityManager;

    private static final int BATCH_SIZE = 5;

    @Transactional
    @Test
    public void whenInsertingSingleTypeOfEntity_thenCreatesSingleBatch() {
        for (int i = 0; i < 10000000; i++) {
            Book book = new Book();
            book.setName("New book batch test");
            entityManager.persist(book);
        }
    }

    @Transactional
    @Test
    public void whenNotConfigured_ThenSendsInsertsSeparately() {
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setName("New book batch test" + i);
            entityManager.persist(book);
        }
        entityManager.flush();
    }
}
