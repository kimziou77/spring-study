package com.bookmanager.domain;

import com.bookmanager.domain.listener.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
//@EntityListeners(value = MyEntityListener.class) // Listener를 통해서 해당 값을 주입을 해줄 수 있다. - 공통부분에서 리스너를 하나만 구현해도 여러곳에서 참조해서 사용가능하다.
@EntityListeners(value = AuditingEntityListener.class)
public class Book extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue // 지금은 h2이기때문에 hibernate에서 지원하는 auto값을 사용함
    private Long id;
    private String name;
    private String author;

    // 거의 모든 entity에서 이것을 다룬다. - 매번 이걸 작성해야 할까? - 1 Entity Listener를 활용한다.2  BaseEntity를 상속받는다.
//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

//
//    @PrePersist
//    public void prePersist(){
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdate(){
//        this.updatedAt = LocalDateTime.now();
//    }
}
