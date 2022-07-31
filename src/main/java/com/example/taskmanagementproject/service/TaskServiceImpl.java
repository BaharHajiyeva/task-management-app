package com.example.taskmanagementproject.service;

import com.example.taskmanagementproject.DTO.TaskDTO;
import com.example.taskmanagementproject.status.TaskStatus;
import com.example.taskmanagementproject.domain.Role;
import com.example.taskmanagementproject.domain.Task;
import com.example.taskmanagementproject.domain.User;
import com.example.taskmanagementproject.mapper.TaskDTOMapper;
import com.example.taskmanagementproject.payload.AssignTaskPayload;
import com.example.taskmanagementproject.payload.CreateTaskPayload;
import com.example.taskmanagementproject.repo.RoleRepo;
import com.example.taskmanagementproject.repo.TaskRepo;
import com.example.taskmanagementproject.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public List<TaskDTO> getTasks() {

        return taskRepo.findAll()
                .stream()
                .map(TaskDTOMapper::mapFromTask)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(long id) {

        try{
            Task task = taskRepo.getReferenceById(id);
            return TaskDTO.getTaskDTO(task);

        } catch(Exception exc){
            log.error("Task not found with id: {}",id);
            throw new RuntimeException("Task not found");
        }
    }

    @Override
    public void assignTask(AssignTaskPayload payload) {
        Optional<Task> optionalTask = taskRepo.findById(payload.getTaskId());

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            User user = userRepo.findByUsername(payload.getUserDetailsPayload().getUsername());
            Collection<User> users = new ArrayList<>();

            if (user != null) {
                Role admin = roleRepo.findByName("ROLE_ADMIN");
                if (user.getRoles().contains(admin)) {

                    log.error("You do not have a permission to assign task to the Admin");
                    throw new RuntimeException("You do not have a permission to assign task to the Admin");
                }
                users.add(user);
            } else {
                log.error("User not found");
                throw new RuntimeException("User not found");
            }
            if (!task.getAssigneeUser().contains(user)) {
                task.setStatus(TaskStatus.ASSIGNED.name());
                task.getAssigneeUser().add(user);
            }

            log.info("Task Assigned To : " + task.getAssigneeUser());


        } else {
            log.info("Task Not Found ");
            throw new RuntimeException("Task Not Found");
        }
    }

    @Override
    public TaskDTO createTask(CreateTaskPayload payload) {

        Task task = new Task();
        String formattedDateTime = payload.getDeadline().format(formatter);

        task.setTitle(payload.getTitle());
        task.setDescription(payload.getDescription());
        task.setStatus(TaskStatus.NOT_ASSIGNED.name());
        task.setDeadline(LocalDateTime.now());
        task.setDeadline(LocalDateTime.parse(formattedDateTime));
        taskRepo.save(task);
        return TaskDTO.create(task);
    }
}
