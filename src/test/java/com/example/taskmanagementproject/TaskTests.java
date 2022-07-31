package com.example.taskmanagementproject;

import com.example.taskmanagementproject.DTO.TaskDTO;
import com.example.taskmanagementproject.payload.AssignTaskPayload;
import com.example.taskmanagementproject.payload.CreateOrganizationPayload;
import com.example.taskmanagementproject.payload.CreateTaskPayload;
import com.example.taskmanagementproject.payload.UserDetailsPayload;
import com.example.taskmanagementproject.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

@SpringBootTest(classes = TaskManagementProjectApplication.class)
public class TaskTests {

    private final String TASK_ROOT_URL = "/api/tasks";

    @Autowired
    TaskService taskService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @AfterEach
    public void init(){

    }

    @BeforeEach
    public void setUp
            (WebApplicationContext context) throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper = Jackson2ObjectMapperBuilder.json().build();

    }


    @Test
    void shouldGetTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(TASK_ROOT_URL).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetTaskByID() throws Exception {

        CreateTaskPayload taskPayload = new CreateTaskPayload() ;
        taskPayload.setDeadline(LocalDateTime.now());
        taskPayload.setDescription("test-description");
        taskPayload.setTitle("title-test");

       var result =  mockMvc.perform(MockMvcRequestBuilders.post(TASK_ROOT_URL)
                       .content(objectMapper.writeValueAsString(taskPayload))
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(ResultMatcher.matchAll())
               .andReturn();

      TaskDTO taskDTO = objectMapper.readValue( result.getResponse().getContentAsString(), TaskDTO.class);

        mockMvc.perform(MockMvcRequestBuilders.get(TASK_ROOT_URL + "/" + taskDTO.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateTask() throws Exception {

        CreateTaskPayload taskPayload = new CreateTaskPayload() ;
        taskPayload.setDeadline(LocalDateTime.now());
        taskPayload.setDescription("test-description");
        taskPayload.setTitle("title-test");

        var result =  mockMvc.perform(MockMvcRequestBuilders.post(TASK_ROOT_URL)
                        .content(objectMapper.writeValueAsString(taskPayload))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(ResultMatcher.matchAll())
                .andReturn();
    }


}
