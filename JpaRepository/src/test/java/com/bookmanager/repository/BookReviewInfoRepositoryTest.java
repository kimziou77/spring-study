package com.bookmanager.repository;

import com.bookmanager.domain.Book;
import com.bookmanager.domain.BookReviewinfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookReviewInfoRepositoryTest {
    @Autowired
    private BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void crudTest(){
        BookReviewinfo bookReviewInfo = new BookReviewinfo();
//        bookReviewInfo.setBookId(1L);
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);
        bookReviewInfoRepository.save(bookReviewInfo);
        System.out.println(">>> "+bookReviewInfoRepository.findAll());
    }

//    @Test
//    void crudTest2_06_02(){ //학습을 위한 테스트
//        givenBookReviewInfo();
//        Book result = bookReviewInfoRepository
//                        .findById(1L)
//                        .orElseThrow(RuntimeException::new)
//                        .getBook();
//
//        System.out.println(">>> " + result);
//
//        BookReviewinfo result2 = bookRepository
//                .findById(8L)
//                .orElseThrow(RuntimeException::new)
//                .getBookReviewinfo();
//        System.out.println(">>> " + result2);
//    }


    private Book givenBook(){
        Book book = new Book();
        book.setName("수빈 만만세");
        book.setAuthorId(1L);
        // book.setPublisherId(1L);
        return bookRepository.save(book);
    }

    private void givenBookReviewInfo(){
        BookReviewinfo bookReviewInfo = new BookReviewinfo();
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);
        bookReviewInfoRepository.save(bookReviewInfo);
        System.out.println(">>> "+bookReviewInfoRepository.findAll());
    }

}