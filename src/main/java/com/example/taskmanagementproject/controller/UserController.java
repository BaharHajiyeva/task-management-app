package com.example.taskmanagementproject.controller;

import com.example.taskmanagementproject.DTO.OrganizationDTO;
import com.example.taskmanagementproject.DTO.UserDTO;
import com.example.taskmanagementproject.payload.CreateOrganizationPayload;
import com.example.taskmanagementproject.payload.CreateUserPayload;
import com.example.taskmanagementproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid CreateUserPayload payload,
                                            @RequestHeader("Authorization") String bearerToken) throws IllegalArgumentException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(payload,bearerToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<OrganizationDTO> signup(@RequestBody @Valid CreateOrganizationPayload payload) {
        return ResponseEntity.ok(userService.signup(payload));
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

}

