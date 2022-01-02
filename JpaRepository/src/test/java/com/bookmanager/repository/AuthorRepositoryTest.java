package com.bookmanager.repository;

import com.bookmanager.domain.Author;
import com.bookmanager.domain.Book;
import com.bookmanager.domain.BookAndAuthor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookAndAuthorRepository bookAndAuthorRepository;

    @Test
    @Transactional
    void manyToManyTest(){
        // many를 다루는데 복잡성이 커지기 때문에 거의 이런 맵핑을 사용하지 않는다.
        // 공동저자 자체를 하나의 저자로 처리해버리는 경우도 생김
        // 예를들어 e-commerce 주문 테이블이 존재한다.
        // User - Product : Many to Many임 : M:N
        // User_products라는 중간테이블이 생기게 될것임. -> order라는 Entity를 만들어서 User:order 은 1:N order:product N:1
        // one to many와 many to one의 조합으로 생성하게 된다.
        // 네이밍을 좀 더 신중하게 해야겠지만 우선은!! BookAndAuthor라는걸로 공부를 해보자.

        Book book1 = givenBook("책1");
        Book book2 = givenBook("책2");
        Book book3 = givenBook("개발책1");
        Book book4 = givenBook("개발책2");
        Author author1 = givenAuthor("martin");
        Author author2 = givenAuthor("steve");

//        book1.addAuthor(author1); // 지저분한 new ArrayList같은것도 지워나가는게 깔끔한 코딩에서 중요한 것일것이다.
//        book2.addAuthor(author2);
//        book3.addAuthor(author1,author2);
//        book4.addAuthor(author1,author2);
//
//        author1.addBook(book1,book3,book4);
//        author2.addBook(book2,book3,book4);
//        author1.setBooks(Lists.newArrayList(book1,book3,book4));
//        author2.setBooks(Lists.newArrayList(book2,book3,book4));
        bookRepository.saveAll(Lists.newArrayList(book1,book2,book3,book4));
        authorRepository.saveAll(Lists.newArrayList(author1,author2));
//
//        System.out.println("authors through book : "+bookRepository.findAll().get(2).getAuthors());
//        System.out.println("books through author : "+authorRepository.findAll().get(0).getBooks());
    }
    @Test
    @Transactional
    void manyToManyTest2(){
        // ManyToOne의 관계로 풀어냈다.
        Book book1 = givenBook("책1");
        Book book2 = givenBook("책2");
        Book book3 = givenBook("개발책1");
        Book book4 = givenBook("개발책2");
        Author author1 = givenAuthor("martin");
        Author author2 = givenAuthor("steve");
        BookAndAuthor bookAndAuthor1 = givenBookAndAuthor(book1, author1);
        BookAndAuthor bookAndAuthor2 = givenBookAndAuthor(book2, author2);
        BookAndAuthor bookAndAuthor3 = givenBookAndAuthor(book3, author1);
        BookAndAuthor bookAndAuthor4 = givenBookAndAuthor(book3, author2);
        BookAndAuthor bookAndAuthor5 = givenBookAndAuthor(book4, author1);
        BookAndAuthor bookAndAuthor6 = givenBookAndAuthor(book4, author2);
        book1.addBookAndAuthors(bookAndAuthor1);
        book2.addBookAndAuthors(bookAndAuthor2);
        book3.addBookAndAuthors(bookAndAuthor3,bookAndAuthor4);
        book4.addBookAndAuthors(bookAndAuthor5,bookAndAuthor6);

        author1.addBookAndAuthors(bookAndAuthor1,bookAndAuthor3,bookAndAuthor5);
        author2.addBookAndAuthors(bookAndAuthor2,bookAndAuthor4,bookAndAuthor6);

        bookRepository.saveAll(Lists.newArrayList(book1,book2,book3,book4));
        authorRepository.saveAll(Lists.newArrayList(author1,author2));

        bookRepository.findAll().get(2).getBookAndAuthors().forEach(o -> System.out.println(o.getAuthor()));
        authorRepository.findAll().get(0).getBookAndAuthors().forEach(o-> System.out.println(o.getBook()));
    }
    private BookAndAuthor givenBookAndAuthor(Book book, Author author){
        BookAndAuthor bookAndAuthor = new BookAndAuthor();
        bookAndAuthor.setBook(book);
        bookAndAuthor.setAuthor(author);
        return bookAndAuthorRepository.save(bookAndAuthor);
    }

    private Book givenBook(String name){
        Book book = new Book();
        book.setName(name);
        return bookRepository.save(book);
    }
    private Author givenAuthor(String name){
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }
}