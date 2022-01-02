package com.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Book extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Long authorId;


    @OneToOne(mappedBy = "book")
    @ToString.Exclude
    private BookReviewinfo bookReviewinfo;

    @OneToMany // One To Many에서는 중간테이블을 만들지 않기 위해서 joinColumn을 해준다.
    @JoinColumn(name="book_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private Publisher publisher;

//    @ManyToMany // many to many 관계에서는 FK를 갖기가 힘들다. 그래서 중간에 맵핑해주는 중간테이블이 하나 필요하다.
    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors){
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }
}
