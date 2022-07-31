package com.example.taskmanagementproject.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserPayload {
    @NotNull(message = "field cannot be empty")
    private String name;
    @NotNull(message = "field cannot be empty")
    private String surname;
    @NotNull(message = "field cannot be empty")
    private String email;
    @NotNull(message = "field cannot be empty")
    private String password;
    @NotNull(message = "field cannot be empty")
    private String username;
}
