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

    private int BATCH_SIZE = 50;


    @Transactional
    @Test
    public void whenInsertingSingleTypeOfEntity_thenCreatesSingleBatch0() {
        for (int i = 0; i < 100000; i++) {
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            Book book = new Book();
            book.setName("New book batch test" + i);
            entityManager.persist(book);
        }
    }
}
