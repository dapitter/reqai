package com.reqai.infrastructure.persistence.project;

import com.reqai.application.project.ProjectRepository;
import com.reqai.domain.project.Project;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepositoryAdapter implements ProjectRepository {

    private final JpaProjectRepository repository;

    public ProjectRepositoryAdapter(JpaProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Project save(Project project) {
        return repository.save(project);
    }
}
