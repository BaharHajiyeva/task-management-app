package com.example.taskmanagementproject.mapper;

import com.example.taskmanagementproject.domain.User;
import com.example.taskmanagementproject.payload.CreateOrganizationPayload;
import com.example.taskmanagementproject.util.EmailValidation;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserMapper {


    public static User mapFromCreateOrganizationPayload(CreateOrganizationPayload payload) {


        if ( EmailValidation.patternMatches(payload.getEmail()) ) {
            User user = new User();
            user.setUsername(payload.getUsername());
            user.setEmail(payload.getEmail());
            return user;
        } else {
            throw new IllegalArgumentException ("please enter the valid email address");
        }

    }
}
