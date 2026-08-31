package com.reqai.infrastructure.persistence.project;

import com.reqai.application.project.ProjectQueryRepository;
import com.reqai.domain.project.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProjectQueryRepositoryAdapter implements ProjectQueryRepository {
    private final JpaProjectRepository repository;

    public ProjectQueryRepositoryAdapter(JpaProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> findByOrganizationId(UUID organizationId) {
        return repository.findByOrganizationId(organizationId);
    }
}
