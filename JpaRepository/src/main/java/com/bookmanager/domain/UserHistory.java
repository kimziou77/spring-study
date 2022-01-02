package com.bookmanager.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class UserHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name= "user_id",insertable = false, updatable = false)
//    private Long userId;
    private String name;
    private String email;

    @ManyToOne
    // @ToString.Exclude // 이거 없으면 롬복에서 제공하는 ToString 메서드가 Entity를 순환참조하기 때문에 오버플로 발생 // 그치만 User에서 Exclude를 시켜주는게 좀 더 깔끔해보임
    private User user; // Entity 값을 주입을 하도록 해보자.
}
