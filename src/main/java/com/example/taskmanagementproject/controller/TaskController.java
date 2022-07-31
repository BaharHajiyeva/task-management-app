package com.example.taskmanagementproject.controller;

import com.example.taskmanagementproject.DTO.TaskDTO;
import com.example.taskmanagementproject.payload.AssignTaskPayload;
import com.example.taskmanagementproject.payload.CreateTaskPayload;
import com.example.taskmanagementproject.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getUsers() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable long  id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskPayload payload) {
        return ResponseEntity.ok(taskService.createTask(payload));
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignTask(@RequestBody @Valid AssignTaskPayload payload) {
        taskService.assignTask(payload);
        return ResponseEntity.ok("Task successfully assigned to user");
    }
}
