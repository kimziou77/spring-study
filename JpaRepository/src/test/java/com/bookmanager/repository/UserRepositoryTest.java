package com.bookmanager.repository;

import com.bookmanager.domain.Gender;
import com.bookmanager.domain.User;
import jdk.vm.ci.meta.Local;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistoryRepository userHistoryRepository;

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
    @Test
    void query() {
        System.out.println("findByEmailAndName : "+ userRepository.findByEmailAndName("martin@fastcampus.com","martin"));
        System.out.println("findByEmailOrName : "+userRepository.findByEmailOrName("martin@fastcampus.com", "bbbb"));
        System.out.println("findByIdAfter : "+userRepository.findByIdAfter(4L)); // Before와 After은 날짜와 시간에 사용하면 좋음
        System.out.println("findByIdGreaterThan :" + userRepository.findByIdGreaterThan(3L));
        System.out.println("findByIdGreaterThanEqual :" + userRepository.findByIdGreaterThanEqual(3L));

        System.out.println("findByIdBetween : "+userRepository.findByIdBetween(1L,3L));

    }
    @Test
    void ch03_03(){
        System.out.println("findByIdIsNotNull : "+userRepository.findByIdIsNotNull());
        // collection type 에 empty를 체크하는 것이라 error가 남. 문자열 empty가 아님
        // System.out.println("findByIdIsNotEmpty : "+userRepository.findByIdIsNotEmpty());

        // 확인을 위해 Address 클래스를 추가해보자
        // System.out.println("findByAdressIsNotEmpty : "+userRepository.findByAddressIsNotEmpty());
        System.out.println("findByNameIn : "+userRepository.findByNameIn(Lists.newArrayList("martin","bbbb")));

        System.out.println("findByNameStartingWith : "+ userRepository.findByNameStartingWith("mar"));
        System.out.println("findByNameEndingWith : "+ userRepository.findByNameEndingWith("tin"));
        System.out.println("findByNameContainsWith : "+ userRepository.findByNameContains("art"));

        System.out.println("findByNameLike : "+userRepository.findByNameLike("%art%"));

    }

    @Test
    void ch03_04(){
        // pagingAndSortingTest()
        System.out.println("findTop1ByName : "+ userRepository.findTop1ByName("martin"));
        System.out.println("findLast1ByName : " + userRepository.findLast1ByName("martin")) ;

        // 의도한것 - 역순으로 만들어서 하나 가지고 오고 싶었음
        System.out.println("findTop1ByNameOrderByIdDesc : " + userRepository.findTopByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc : " + userRepository.findFirstByNameOrderByIdDescEmailAsc("martin"));
        //점점 이름이 길어지고 있음.
        // 밑에 보면 인터페이스는 findFirstByName 이거 하나 이고 나머지는 사용자가 선택할 수 있도록 만들어줌.

        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));
        /* 아래와 같은 함수를 만들어서 위에 대입해 주면 좋을듯
        private Sort getSort(){
             return Sort.by(Order.desc("id"),
                            Order.asc("email"),
                            Order.asc("updatedAt"));
         */
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

        //update
        User user2= userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrtin");
        userRepository.save(user2);

        //enum
        User user3 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user3.setGender(Gender.MALE);
        userRepository.save(user);
        userRepository.findAll().forEach(System.out::println);
        //현업에서 자주 발생하는 장애포인트가 있음 
        // jpa에서 이넘을 어떻게 다루는지 개념이 부족함
        // 아직은 h2로 테스트를 하고있는데 native 쿼리를 통해서 결과를 확인해보면
        // System.out.println(userRepository.findRawRecord().get("gender"));
        // Ordinal한 값이기 때문에... MALE, FEMALE 넣었을때 0,1이었는데 맨앞에 BABY 추가하면 값이 바뀜
        // 그래서 ENUM을 사용할때 반드시! ENUM의 타입을 @Enumerated(VALUE = EnumType.STRING)으로 바꿔주기!
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
//        user3.setCreatedAt(LocalDateTime.now()); // 설정을 안하면 데이터에 정합성에도 문제가 생기게 됨
//        user3.setUpdatedAt(LocalDateTime.now()); // 실제 언제 생기고 수정했는지 알수 없음 -> prePersist함수에 작성
        // prePersist를 사용하게 되면 매번 정확한 값이 들어가며, 개발자들이 넣는걸 까먹는것도 방지할 수 있다.

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

        /*
        Entity 생애주기 안에서 발생하는 이벤트에 따라서 추가적으로 발생할수있는 entity listener
        객체 내부에서 선언
        별도의 클래스에서 만드는것
        jpa에서 제공하는 listener까지 배웠다.
        */


    }

}