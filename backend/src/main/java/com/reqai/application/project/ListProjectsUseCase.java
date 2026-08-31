package com.reqai.application.project;

import com.reqai.domain.project.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListProjectsUseCase {
    private final ProjectQueryRepository repository;

    public ListProjectsUseCase(ProjectQueryRepository repository) {
        this.repository = repository;
    }

    public List<Project> execute(UUID organizationId) {
        return repository.findByOrganizationId(organizationId);
    }
}
