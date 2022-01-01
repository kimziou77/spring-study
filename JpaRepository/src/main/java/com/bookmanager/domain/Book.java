package com.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

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
    private Long publisherId;

    // 여기에 mappedBy ="book"하면 StackOverflow 에러가 남.
    // 특별히 필요한 경우가 아니라면  relation은 단방향으로 걸거나 toString에서 제외하는게 필요하다.
    @OneToOne(mappedBy = "book")
    @ToString.Exclude
    private BookReviewinfo bookReviewinfo;

}
