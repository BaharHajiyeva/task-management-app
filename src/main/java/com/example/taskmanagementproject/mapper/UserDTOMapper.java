package com.example.taskmanagementproject.mapper;

import com.example.taskmanagementproject.DTO.OrganizationDTO;
import com.example.taskmanagementproject.DTO.RoleDTO;
import com.example.taskmanagementproject.DTO.UserDTO;
import com.example.taskmanagementproject.domain.Organization;
import com.example.taskmanagementproject.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTOMapper {

    public static OrganizationDTO mapToOrganizationDto(User user, Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();

        organizationDTO.setId(organization.getId());
        organizationDTO.setOrganizationName(organization.getOrganizationName());
        organizationDTO.setPhone(organization.getPhone());
        organizationDTO.setAddress(organization.getAddress());
        organizationDTO.setUsername(user.getUsername());
        organizationDTO.setEmail(user.getEmail());

        List<RoleDTO> roleDTOS = getRoleDTOs(user);
        organizationDTO.setRoles(roleDTOS);

        return organizationDTO;

    }

    public static UserDTO mapToUserDto(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        List<RoleDTO> roleDTOS = getRoleDTOs(user);
        userDTO.setRoles(roleDTOS);

        return userDTO;
    }

    private static List<RoleDTO> getRoleDTOs(User user) {
        return user.getRoles()
                .stream()
                .map(RoleMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
