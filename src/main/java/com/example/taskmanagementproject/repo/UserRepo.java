package com.example.taskmanagementproject.repo;

import com.example.taskmanagementproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    long countByUsername(String username);

    long countByEmail(String email);

}
