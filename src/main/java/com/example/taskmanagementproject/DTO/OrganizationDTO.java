package com.example.taskmanagementproject.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class OrganizationDTO {
    private Long id;
    private String organizationName;
    private String phone;
    private String address;
    private String username;
    private String email;
    private Collection<RoleDTO> roles = new ArrayList<>();
}
