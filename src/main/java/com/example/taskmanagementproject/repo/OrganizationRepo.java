package com.example.taskmanagementproject.repo;

import com.example.taskmanagementproject.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepo extends JpaRepository<Organization, Long> {

    Organization findByEmail(String email);

    long countByEmail(String email);


}
