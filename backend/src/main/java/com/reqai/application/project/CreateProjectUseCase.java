package com.reqai.application.project;

import com.reqai.domain.project.Project;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    public CreateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project execute(UUID organizationId, String name, String description, String domainContext) {
        Project project = new Project(organizationId, name, description, domainContext);
        return projectRepository.save(project);
    }
}
