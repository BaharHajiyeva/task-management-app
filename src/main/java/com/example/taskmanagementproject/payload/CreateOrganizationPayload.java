package com.example.taskmanagementproject.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CreateOrganizationPayload {

    private Long id;
    @NotNull(message = "field cannot be empty")
    private String organizationName;
    @NotNull(message = "field cannot be empty")
    private String phone;
    @NotNull(message = "field cannot be empty")
    private String address;
    @NotNull(message = "field cannot be empty")
    private String username;
    @NotNull(message = "field cannot be empty")
    private String email;
    @NotNull(message = "field cannot be empty")
    private String password;
}
