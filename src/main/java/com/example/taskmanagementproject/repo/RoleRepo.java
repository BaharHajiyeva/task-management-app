package com.example.taskmanagementproject.repo;

import com.example.taskmanagementproject.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
