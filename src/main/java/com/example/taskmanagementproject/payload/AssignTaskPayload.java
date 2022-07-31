package com.example.taskmanagementproject.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AssignTaskPayload {

    @NotNull(message = "field cannot be empty")
    private long taskId;

    @NotNull(message = "field cannot be empty")
    private UserDetailsPayload userDetailsPayload;
}
