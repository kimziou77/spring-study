package com.bookmanager.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class UserTest {

    @Test
    void test(){
        User user = new User();
        user.setEmail("kimziou77@naver.com");
        user.setName("martin");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User user1 = new User(null, "martin", "kimziou77@naver.com", LocalDateTime.now(),LocalDateTime.now());
        User user2 = new User("martin","kimziou77@naver.com");

        User user3 = User.builder()
                .name("martin")
                .email("kimziou77@NAVER.COM")
                .build();

        System.out.println(user);
    }
}