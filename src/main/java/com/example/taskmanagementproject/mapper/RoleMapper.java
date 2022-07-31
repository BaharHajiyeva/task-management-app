package com.example.taskmanagementproject.mapper;

import com.example.taskmanagementproject.DTO.RoleDTO;
import com.example.taskmanagementproject.domain.Role;

public class RoleMapper {

    public static RoleDTO mapToDto(Role role){
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setName(role.getName());
        return  roleDTO;
    }
}
