package com.example.taskmanagementproject.service;

import com.example.taskmanagementproject.DTO.OrganizationDTO;
import com.example.taskmanagementproject.DTO.UserDTO;
import com.example.taskmanagementproject.payload.CreateOrganizationPayload;
import com.example.taskmanagementproject.payload.CreateUserPayload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    OrganizationDTO signup(CreateOrganizationPayload payload) throws IllegalArgumentException;
    UserDTO saveUser(CreateUserPayload payload, String bearerToken) throws IllegalArgumentException;
    List<UserDTO> getUsers();
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
