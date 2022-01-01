package com.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor //Entity는 요게 있어야됨
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewinfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // private Long bookId;

    // optional : 반드시 존재해야 하는 값 null 허용 x
    // mappedBy : 여기서는 book_id를 가지고 있지 않게 됨 , mappedBy = "bookReviewInfo"
    @OneToOne(optional = false)
    private Book book;  //자바 객체의 속성에 따라서 최적의 DDL혹은 select쿼리들을 만들어서 실행시켜주는 것이 jpa의 역할이다.
    //지금까지는 bookreview에서 book 정보를 가져오는걸 보았음. book에서 book review인포를 가져오려면?

    // null을 허용할지에 따라서 원시타입, 래퍼타입을 선택한다.
    // 지금같은 경우는 0을 넣어줄거기 때문에 원시타입을 쓰지만, Float같은 경우에는 null체크 안하면 Exception이 발생할 수 있으므로 유의한다.
    // 원시타입으로 테이블 생성시 not null이 붙는다.
    private float averageReviewScore;
    private int reviewCount;
}
