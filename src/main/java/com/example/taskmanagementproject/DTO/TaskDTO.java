package com.example.taskmanagementproject.DTO;

import com.example.taskmanagementproject.domain.Task;
import com.example.taskmanagementproject.mapper.UserDTOMapper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private String status;
    private Collection<UserDTO> assigneeUser;

    public static TaskDTO create(Task task) {
        TaskDTO taskDto = new TaskDTO();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setAssigneeUser(null);
        return  taskDto;
    }

    public static TaskDTO getTaskDTO(Task task) {
        TaskDTO taskDto = new TaskDTO();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setDeadline(LocalDateTime.now());
        taskDto.setAssigneeUser(getUserDtos(task));
        return  taskDto;
    }

    public static List<UserDTO> getUserDtos(Task task) {
        return task.getAssigneeUser()
                .stream()
                .map(UserDTOMapper::mapToUserDto)
                .collect(Collectors.toList());
    }




}
