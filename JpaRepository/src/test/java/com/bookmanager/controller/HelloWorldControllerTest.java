package com.bookmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class HelloWorldControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void helloWorldTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello-world"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello-world"));
    }
}