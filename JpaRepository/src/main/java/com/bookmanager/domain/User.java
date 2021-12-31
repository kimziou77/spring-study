package com.bookmanager.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor //NonNull로 지정된 애들만 따로 만들어주는 생성자 - @Data에 들어가있음.
@Builder
@Entity // pk가 반드시 필요하다는 뜻임
public class User{
    @Id
    @GeneratedValue // 자동으로 증가하는 숫자값
    private Long id;     //이 객체를 어떻게 조회하고 저장할 수 있을까요?
    //repository를 만들면 된다

    @NonNull
    private String name;
    @NonNull
    private String email;
    // data base에 언제 생성되었는지 이력들을 관리하기 위해서
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 객체지향의 기본적인 캡슐화 원칙을 지키기 위해서 getter와 setter를 만들어 주어야 한다.

//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Address> address;

}
