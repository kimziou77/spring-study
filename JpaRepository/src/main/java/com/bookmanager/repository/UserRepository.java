package com.bookmanager.repository;

import com.bookmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    List<User> findByIdIsNotNull();

    // isNotEmpty 잘쓰이는 구문이 아님
    // List<User> findByAddressIsNotEmpty(); // name is not null and name != ''?? xx
    List<User> findByNameIn(List<String> names);

    List<User> findByNameStartingWith(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameContains(String name);

    List<User> findByNameLike(String name);
    Set<User> findUserByNameIs(String name);
    Set<User> findUserByName(String name);
    Set<User> findUserByNameEquals(String name);

    List<User> findTop1ByName(String name);

    List<User> findTopByNameOrderByIdDesc(String name);
    List<User> findTopByNameOrderByIdAsc(String name);

    List<User> findFirstByNameOrderByIdDescEmailAsc(String name);
    List<User> findFirstByName(String name, Sort sort);

    Page<User> findByName(String name, Pageable pageable);
}
