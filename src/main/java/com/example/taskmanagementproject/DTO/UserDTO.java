package com.example.taskmanagementproject.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String username;
    private Collection<RoleDTO> roles = new ArrayList<>();

}
