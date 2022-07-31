package com.example.taskmanagementproject.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDetailsPayload {

    @NotNull(message = "field cannot be empty")
    private String username;
    private String name;
    private String surname;
    private String email;

}
