package com.bookmanager.repository;

import com.bookmanager.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("Subin");
        book.setAuthorId(1L);
        book.setPublisherId(1L);
        bookRepository.save(book);
        System.out.println(bookRepository.findAll());
    }
}
