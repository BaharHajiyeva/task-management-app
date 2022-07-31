package com.example.taskmanagementproject.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateTaskPayload {
    private Long id;
    @NotNull(message = "field cannot be empty")
    private String title;
    @NotNull(message = "field cannot be empty")
    private String description;
    @NotNull(message = "field cannot be empty")
    private LocalDateTime deadline;
}
