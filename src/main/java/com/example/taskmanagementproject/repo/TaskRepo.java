package com.example.taskmanagementproject.repo;

import com.example.taskmanagementproject.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {

}
