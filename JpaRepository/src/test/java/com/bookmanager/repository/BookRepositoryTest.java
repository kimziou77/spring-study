package com.bookmanager.repository;

import com.bookmanager.domain.Book;
import com.bookmanager.domain.Publisher;
import com.bookmanager.domain.Review;
import com.bookmanager.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("Subin");
        book.setAuthorId(1L);
        // book.setPublisherId(1L);
        bookRepository.save(book);
        System.out.println(bookRepository.findAll());
    }
    @Test
    @Transactional
    void bookRelationTest(){
        givenBookAndReview();
        User user = userRepository.findByEmail("martin@fastcampus.com");
        System.out.println("Review : "+ user.getReviews());
        System.out.println("Book : "+ user.getReviews().get(0).getBook());
        System.out.println("Publisher : "+user.getReviews().get(0).getBook().getPublisher());
    }

    private void givenBookAndReview(){
        givenReview(givenUser(), givenBook(givenPublisher()));
    }
    private Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("fastcampus");
        return publisherRepository.save(publisher);
    }
    private Book givenBook(Publisher publisher){
        Book book = new Book();
        book.setName("스프링 공부중");
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }
    private User givenUser(){
        return userRepository.findByEmail("martin@fastcampus.com");
    }
    private void givenReview(User user, Book book){
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("너무너무 재미있고 즐거운 책이었어요");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);
        reviewRepository.save(review);
    }
}
