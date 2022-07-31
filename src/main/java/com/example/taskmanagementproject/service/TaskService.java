package com.example.taskmanagementproject.service;

import com.example.taskmanagementproject.DTO.TaskDTO;
import com.example.taskmanagementproject.payload.AssignTaskPayload;
import com.example.taskmanagementproject.payload.CreateTaskPayload;

import java.util.List;

public interface TaskService {

    List<TaskDTO> getTasks();
    TaskDTO getTaskById(long id);
    void assignTask(AssignTaskPayload payload);
    TaskDTO createTask(CreateTaskPayload payload);


}
