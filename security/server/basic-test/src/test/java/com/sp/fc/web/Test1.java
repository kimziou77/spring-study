package com.sp.fc.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test1 {

    @Test
    @DisplayName(("1. 테스트"))
    void test1(){
        assertEquals("test", "test");
    }
}
