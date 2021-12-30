package com.bookmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest // mvc test를 할 수 있도록 mock mvc를만들어줌
class HelloWorldControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void helloWorldTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello-world"))
                .andDo(print())
                .andExpect(status().isOk()) //200응답 리턴할거임
                .andExpect(content().string("hello-world")); //리턴되는 결과값 확인
    }
}