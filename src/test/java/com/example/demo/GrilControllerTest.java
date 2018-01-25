package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

/**
 * controller测试方法（类似：postman）
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc//模拟
public class GrilControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void helloAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello").headers(null)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string("abc:1234789"));
    }


    @Test
    public void helloAdd1() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/hello")).
                andExpect(MockMvcResultMatchers.status().isOk());
//                andExpect(MockMvcResultMatchers.jsonPath("$.count"))
    }

}