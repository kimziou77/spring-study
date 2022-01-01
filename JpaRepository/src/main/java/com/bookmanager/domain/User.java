package com.bookmanager.domain;

import com.bookmanager.domain.listener.Auditable;
import com.bookmanager.domain.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor //NonNull로 지정된 애들만 따로 만들어주는 생성자 - @Data에 들어가있음.
@Builder
@Entity // pk가 반드시 필요하다는 뜻임  // table과 entity는 이름이 1:1로 대응되어야 한다..
//@EntityListeners(value = {MyEntityListener.class, UserEntityListener.class})
//@EntityListeners(value = {AuditingEntityListener.class, UserEntityListener.class})
@EntityListeners(value = UserEntityListener.class)
@Table(name = "user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue // - 개발자가 셋팅하지 않고 자동으로 생성됨 // IDENTITY 등등,,, //default는 AUTO - 각db에 적합한 값을 만들어줌
    private Long id;     //이 객체를 어떻게 조회하고 저장할 수 있을까요?
    //repository를 만들면 된다

    @NonNull
    private String name;
    @NonNull
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    // data base에 언제 생성되었는지 이력들을 관리하기 위해서
//    @Column(nullable=true) // nullable : false 일때 not null 설정해줌 (default - true)
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    // 객체지향의 기본적인 캡슐화 원칙을 지키기 위해서 getter와 setter를 만들어 주어야 한다.

//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Address> address;
    @Transient
    private String testData;
    // 테스트데이터는 디비에 반영하지 않고싶은 속성일 수 있음 그때 붙여줌
    // 영속성에 반영되지 않고 해당객체랑 생명주기를 같이하는 객체라는 어노테이션임

    //이넘처리 - 엔티티에서는 별도의 처리를 한다.

    // Listener
    //jpa에서 제공하는 이벤트는 총 7개가 있다.
    // 현업에서 많이 사용하는 어노테이션 2가지
    // 오디팅 - 감시한다 - createdAt, updatedAt 생성시점과 업데이트 시점은 유저에서 중요한 정보
//    @PrePersist // Insert Method가 실행되기 전에 실행되는 메소드 , Persist :
//    @PreUpdate // Merge Method가 실행되기 전에 실행되는 메소드

//    @PreRemove // Delete 메소드가 실행되기 전
//    @PostPersist // Persist Method가 실행된 후에 실행되는 메소드
//    @PostUpdate // Merge Mehtod가 실행된
//    @PostRemove // Delete 메소드가 실행된 이후
//    @PostLoad // Select 조회가 일어난 직후에 실행되는 메소드

//    @PrePersist
//    public void prePersist(){
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//        System.out.println(">>> prePersist");
//    }
    @PostPersist
    public void postPersist(){
        System.out.println(">>>postPersist");
    }
//
//    @PreUpdate
//    public void preUpdate(){
//        this.updatedAt = LocalDateTime.now();
//        System.out.println(">>>preUpdate");
//    }
    @PostUpdate
    public void postUpdate(){
        System.out.println(">>>postUpdate");
    }

    @PreRemove
    public void PreRemove(){
        System.out.println(">>> preRemove");
    }
    @PostRemove
    public void postRemove(){
        System.out.println(">>> postRemove");
    }
    @PostLoad
    public void postLoad(){
        System.out.println(">>> postLoad");
    }

}
