package com.example.taskmanagementproject.mapper;

import com.example.taskmanagementproject.DTO.TaskDTO;
import com.example.taskmanagementproject.domain.Task;

public class TaskDTOMapper {

    public static TaskDTO mapFromTask(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setAssigneeUser(TaskDTO.getUserDtos(task));

        return taskDTO;
    }
}
