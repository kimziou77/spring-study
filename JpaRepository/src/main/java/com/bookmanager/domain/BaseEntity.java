package com.bookmanager.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 그간의 반복되는 것을 리팩토링 해보자.
@Data
@MappedSuperclass// 여기서 가장 중요함 - 해당클래스의 필드를 상속받는 엔티티의 컬럼으로 포함시켜주겠다는 뜻임.
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
