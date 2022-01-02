package com.bookmanager.domain;

import com.bookmanager.domain.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@EntityListeners(value = UserEntityListener.class)
@Table(name = "user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable =false, updatable = false)
    private List<UserHistory> userHistories = new ArrayList<>(); // JPA에서 persist할때 nullpointer exception이 날 수 있음 따라서 기본생성자를 넣어주면 좋다.
    // userHistories or userHistoryList - 복수형을 쓰는것이 최근 트렌드임

    // 이 전에 create table user_user_histories라는 중간 매핑 테이블이 생겨났음
    // 따라서 JoinColumn을 통해 해당필드의 네임을 활용해서 컬럼을 만들어주고 이상한테이블을 없앤다.
    // 만들어진 컬럼의 이름을 user_id라고 명명한다.
}
