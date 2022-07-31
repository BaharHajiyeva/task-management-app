package com.example.taskmanagementproject.service;

import com.example.taskmanagementproject.DTO.TaskDTO;
import com.example.taskmanagementproject.domain.Task;
import com.example.taskmanagementproject.domain.User;
import com.example.taskmanagementproject.repo.RoleRepo;
import com.example.taskmanagementproject.repo.TaskRepo;
import com.example.taskmanagementproject.repo.UserRepo;
import com.example.taskmanagementproject.status.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepo taskRepo;

    @Test
    public void testGetTasks() {

        Collection<User> users = new ArrayList<>();

        Task task1 = new Task((long) 1, "test-title", "test-desc", LocalDateTime.now(), TaskStatus.NOT_ASSIGNED.name(), users);
        Task task2 = new Task((long) 2, "test-title 2", "test-desc 2", LocalDateTime.now(), TaskStatus.NOT_ASSIGNED.name(), users);

        Mockito.when(taskRepo.findAll()).thenReturn(Arrays.asList(task1, task2));


        List<TaskDTO> result = taskService.getTasks();

        assertEquals(result.get(0).getTitle(), task1.getTitle());


    }



}
