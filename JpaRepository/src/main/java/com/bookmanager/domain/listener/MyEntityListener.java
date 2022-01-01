package com.bookmanager.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MyEntityListener { // JPA에서 기본으로 제공하고 있음 AuditingEntityListener라고 있음 Main에 @EnableJpaAuditing 추가해주기
    @PrePersist
    public void prePersist(Object o){ // Entity의 경우에는 this가 있지만, 얘는 Listener라서 Object인자가 필요함.
        if (o instanceof Auditable){
            ((Auditable)o).setCreatedAt(LocalDateTime.now());
            ((Auditable)o).setUpdatedAt(LocalDateTime.now());
        }
    }
    @PreUpdate
    public void preUpdate(Object o){
        if(o instanceof Auditable){
            ((Auditable)o).setUpdatedAt(LocalDateTime.now());
        }
    }
}
