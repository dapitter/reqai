package com.reqai.infrastructure.persistence.project;

import com.reqai.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProjectRepository extends JpaRepository<Project, UUID> {
}
