package com.example.taskmanagementproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = TaskManagementProjectApplication.class)
public class UserTests {

    private final String USERS_ROOT_URL = "/api/users";

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp(WebApplicationContext context) throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    void shouldGetUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(USERS_ROOT_URL).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
