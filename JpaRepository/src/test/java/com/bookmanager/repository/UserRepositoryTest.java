package com.bookmanager.repository;

import com.bookmanager.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void crud(){ //create, read, update, delete
        User user1 = new User("jack", "jack@asd.com");
        User user2 = new User("seve","seve@dfa.com");
        userRepository.saveAll(Lists.newArrayList(user1,user2));
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }
    @Test
    @Transactional
    void crud2(){
        //get One은 lazy fetch를 지원하고 있따
        User user = userRepository.getOne(1L);
        System.out.println(user);
    }
    @Test
    void crud3(){
        //1번 3번 5번 select해서 가져오기
        List<User> users = userRepository.findAllById(Lists.newArrayList(1L,3L,5L));
    }
    @Test
    void crud4(){
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
        users.forEach(System.out::println);
        userRepository.save(new User());
        userRepository.findAll().forEach(System.out::println);
        // findAll : 실제서비스에서는  성능적인 이슈가 있어서 잘 사용하지 않는다
    }

    @Test
    void crud5(){
        //getOne이 아니라 findById(1L)하면 에러남
        //왜냐면 리턴되는 객체가 Optional로 맵핑이 되어있기 떄문
        User user = userRepository.findById(1L).orElse(null);
        //아이디가 1인애가 없으면 Null을 반환하도록 설정
        System.out.println(user);
    }

    @Test
    void crud6(){
//        userRepository.save(new User("new martin","nwe@dad.com"));
//        userRepository.flush(); // 들어갔다는 말이 없어짐
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
        // jpa에서 페이징은 굉장히 편리하게 제공되고 있따.
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

    /**update**/
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
        System.out.println("getByEmail : " + userRepository.getByEmail("martin@fastcampus.com"));        System.out.println("findByEmail : " + userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println("readByEmail : " + userRepository.readByEmail("martin@fastcampus.com"));
        System.out.println("queryByEmail : " + userRepository.queryByEmail("martin@fastcampus.com"));
        System.out.println("searchByEmail : " + userRepository.searchByEmail("martin@fastcampus.com"));
        System.out.println("streamByEmail : " + userRepository.streamByEmail("martin@fastcampus.com"));
        System.out.println("findUserByEmail : " + userRepository.findUserByEmail("martin@fastcampus.com"));
        System.out.println("findSomethingByEmail : " + userRepository.findSomethingByEmail("martin@fastcampus.com"));

        System.out.println("findTop2ByName : "+ userRepository.findTop2ByName("martin"));
        System.out.println("findFirst2ByName : "+ userRepository.findFirst2ByName("martin"));
        System.out.println("findLast1ByName : "+userRepository.findLast1ByName("martin"));
        // 인식하지 않는 키워드는 무시하게 됨 findByName으로만 동작 (Last1 무시)
        // 역순정렬후 find1


    }
}