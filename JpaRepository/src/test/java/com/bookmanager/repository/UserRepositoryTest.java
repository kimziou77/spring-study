package com.bookmanager.repository;

import com.bookmanager.domain.Gender;
import com.bookmanager.domain.User;
import com.bookmanager.domain.UserHistory;
import jdk.vm.ci.meta.Local;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static java.time.LocalDateTime.now;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    void crud(){
        User user1 = new User("jack", "jack@asd.com");
        User user2 = new User("seve","seve@dfa.com");
        userRepository.saveAll(Lists.newArrayList(user1,user2));
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }
    @Test
    @Transactional
    void crud2(){
        User user = userRepository.getOne(1L);
        System.out.println(user);
    }
    @Test
    void crud3(){
        List<User> users = userRepository.findAllById(Lists.newArrayList(1L,3L,5L));
    }
    @Test
    void crud4(){
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
        users.forEach(System.out::println);
        userRepository.save(new User());
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void crud5(){
        User user = userRepository.findById(1L).orElse(null);
        System.out.println(user);
    }

    @Test
    void crud6(){
        userRepository.saveAndFlush(new User("new martin","nwe@dad.com"));
        userRepository.findAll().forEach(System.out::println);
        System.out.println(userRepository.count());
        System.out.println(userRepository.existsById(1L));
    }

    @Test
    void crud7(){
        boolean exists = userRepository.existsById(1L);
        System.out.println(exists);

        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));
        userRepository.deleteAll();
        System.out.println(userRepository.count());

    }
    @Test
    void crud8(){
        Page<User> users = userRepository.findAll(PageRequest.of(1,3));

        System.out.println("page"+users);
        System.out.println("totalElements : "+users.getTotalElements());
        System.out.println("totalPages : "+users.getTotalPages());
        System.out.println("numberOfElements : "+users.getNumberOfElements());
        System.out.println("sort : "+ users.getSort());
        System.out.println("size : " + users.getSize());
        users.getContent().forEach(System.out::println);
    }

    @Test
    void crud9(){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name")
                .withMatcher("email",endsWith());
        Example<User> example = Example.of(new User("aa","fastcampus.com"),matcher);
        userRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void crud10(){
        userRepository.save(new User("david","davied@Fastcampus.com"));
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setEmail("martin-updated@fastcampuscom");
        userRepository.save(user);
    }
    @Test
    void selectTest(){
        System.out.println(userRepository.findByName("martin"));

        System.out.println("findByEmail : " + userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println("getByEmail : " + userRepository.getByEmail("martin@fastcampus.com"));
        System.out.println("findByEmail : " + userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println("readByEmail : " + userRepository.readByEmail("martin@fastcampus.com"));
        System.out.println("queryByEmail : " + userRepository.queryByEmail("martin@fastcampus.com"));
        System.out.println("searchByEmail : " + userRepository.searchByEmail("martin@fastcampus.com"));
        System.out.println("streamByEmail : " + userRepository.streamByEmail("martin@fastcampus.com"));
        System.out.println("findUserByEmail : " + userRepository.findUserByEmail("martin@fastcampus.com"));
        System.out.println("findSomethingByEmail : " + userRepository.findSomethingByEmail("martin@fastcampus.com"));

        System.out.println("findTop2ByName : "+ userRepository.findTop2ByName("martin"));
        System.out.println("findFirst2ByName : "+ userRepository.findFirst2ByName("martin"));
        System.out.println("findLast1ByName : "+userRepository.findLast1ByName("martin"));
    }
    @Test
    void query() {
        System.out.println("findByEmailAndName : "+ userRepository.findByEmailAndName("martin@fastcampus.com","martin"));
        System.out.println("findByEmailOrName : "+userRepository.findByEmailOrName("martin@fastcampus.com", "bbbb"));
        System.out.println("findByIdAfter : "+userRepository.findByIdAfter(4L));
        System.out.println("findByIdGreaterThan :" + userRepository.findByIdGreaterThan(3L));
        System.out.println("findByIdGreaterThanEqual :" + userRepository.findByIdGreaterThanEqual(3L));

        System.out.println("findByIdBetween : "+userRepository.findByIdBetween(1L,3L));

    }
    @Test
    void ch03_03(){
        System.out.println("findByIdIsNotNull : "+userRepository.findByIdIsNotNull());
        System.out.println("findByNameIn : "+userRepository.findByNameIn(Lists.newArrayList("martin","bbbb")));

        System.out.println("findByNameStartingWith : "+ userRepository.findByNameStartingWith("mar"));
        System.out.println("findByNameEndingWith : "+ userRepository.findByNameEndingWith("tin"));
        System.out.println("findByNameContainsWith : "+ userRepository.findByNameContains("art"));

        System.out.println("findByNameLike : "+userRepository.findByNameLike("%art%"));

    }

    @Test
    void ch03_04(){
        System.out.println("findTop1ByName : "+ userRepository.findTop1ByName("martin"));
        System.out.println("findLast1ByName : " + userRepository.findLast1ByName("martin")) ;

        System.out.println("findTop1ByNameOrderByIdDesc : " + userRepository.findTopByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc : " + userRepository.findFirstByNameOrderByIdDescEmailAsc("martin"));

        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));
    }

    @Test
    void ch03_05(){
        System.out.println("findByNameWithPaging : "+ userRepository.findByName("martin",PageRequest.of(0,1,Sort.by(Sort.Order.desc("id")))).getContent());
        System.out.println("findByNameWithPaging : "+ userRepository.findByName("martin",PageRequest.of(0,1,Sort.by(Sort.Order.desc("id")))).getTotalElements());
    }

    @Test
    void ch04(){
        User user = new User();
        user.setName("martin3");
        user.setEmail("martin2@fastcampus.com");
        user.setCreatedAt(now());
        user.setUpdatedAt(now());
        userRepository.save(user);

        User user2= userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrtin");
        userRepository.save(user2);

        User user3 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user3.setGender(Gender.MALE);
        userRepository.save(user);
        userRepository.findAll().forEach(System.out::println);
    }
    @Test
    void ch05_1(){
        User user = new User();
        user.setEmail("marasd@naver.com");
        user.setName("martin");
        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrtin");
        userRepository.save(user2);
        userRepository.deleteById(4L);

        User user3 =  new User();
        user3.setEmail("martin2@fastcampus@naver.com");
        user3.setName("martin");

        userRepository.save(user3);
        System.out.println(userRepository.findByEmailAndName("martin2@fastcampus@naver.com","martin"));

        User user4 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println("as-is : "+ user);
        user4.setName("martin4");
        userRepository.save(user4);
        System.out.println("to-be : "+userRepository.findAll().get(0));
    }

    @Test
    public void  ch05_2(){
        User user = new User();
        user.setEmail("martin-new@fastcampus.com");
        user.setName("martin-new");
        userRepository.save(user);

        user.setName("martin-new-new");
        userRepository.save(user);
        userHistoryRepository.findAll().forEach(System.out::println);
    }

    // ch06-3 1:N 연관관계 살펴보기
    @Test
    void userRelationTest(){
        User user = new User();
        user.setName("david");
        user.setEmail("david@naver.com");
        user.setGender(Gender.MALE);
        userRepository.save(user);

        user.setName("daniel");
        userRepository.save(user);

        user.setEmail("daniel@fastcampus.com");
        userRepository.save(user);

        List<UserHistory> result = userRepository.findByEmail("daniel@fastcampus.com").getUserHistories();
        result.forEach(System.out::println);

        System.out.println("UserHistory.getUser() : "+ userHistoryRepository.findAll().get(0).getUser());
    }
}