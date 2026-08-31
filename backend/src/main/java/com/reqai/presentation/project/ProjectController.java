package com.reqai.presentation.project;

import com.reqai.application.project.CreateProjectUseCase;
import com.reqai.domain.project.Project;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;

    public ProjectController(CreateProjectUseCase createProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse create(@Valid @RequestBody CreateProjectRequest request) {
        Project project = createProjectUseCase.execute(
                request.organizationId(),
                request.name(),
                request.description(),
                request.domainContext()
        );
        return ProjectResponse.from(project);
    }

    public record CreateProjectRequest(
            @NotNull UUID organizationId,
            @NotBlank @Size(max = 160) String name,
            String description,
            String domainContext
    ) {}

    public record ProjectResponse(
            UUID id,
            UUID organizationId,
            String name,
            String description,
            String domainContext
    ) {
        static ProjectResponse from(Project project) {
            return new ProjectResponse(
                    project.getId(),
                    project.getOrganizationId(),
                    project.getName(),
                    project.getDescription(),
                    project.getDomainContext()
            );
        }
    }
}
