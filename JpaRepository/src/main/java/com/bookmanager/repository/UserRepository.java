package com.bookmanager.repository;

import com.bookmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    //Query메소드 만들기
    List<User> findByName(String name); // 이름을 통해 User을 가져오는 method
    User findByEmail(String email);
    User getByEmail(String email);
    User readByEmail(String email);
    User queryByEmail(String email);
    User searchByEmail(String email);
    User streamByEmail(String email);
    User findUserByEmail(String email);
    User findSomethingByEmail(String email);
    List<User> findFirst2ByName(String name);
    List<User> findTop2ByName(String name);

    List<User> findLast1ByName(String name);

    List<User> findByEmailAndName(String email, String name);

    List<User> findByEmailOrName(String email, String name);

    // List<User> findByCreatedAtAfter(LocalDateTime yesterday);

    List<User> findByIdAfter(Long id);

    // List<User> findByCreatedAtGreaterThanAnd(LocalDateTime yesterday);
    List<User> findByIdGreaterThan(Long id);
    List<User> findByIdGreaterThanEqual(Long id);
    List<User> findByIdBetween(Long id1, Long id2);
}
