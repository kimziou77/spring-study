package com.bookmanager.domain;

import com.bookmanager.domain.listener.Auditable;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/*
 특정값이 수정이 되면
 수정본을 다른테이블에 복사해놓는 테이블이 있음
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(value = MyEntityListener.class)
@EntityListeners(value = AuditingEntityListener.class)
public class UserHistory extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String name;
    private String email;
//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;
}
