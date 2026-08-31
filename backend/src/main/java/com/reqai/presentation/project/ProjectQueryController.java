package com.reqai.presentation.project;

import com.reqai.application.project.ListProjectsUseCase;
import com.reqai.domain.project.Project;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectQueryController {
    private final ListProjectsUseCase useCase;

    public ProjectQueryController(ListProjectsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<ProjectResponse> list(@RequestParam @NotNull UUID organizationId) {
        return useCase.execute(organizationId).stream()
                .map(ProjectResponse::from)
                .toList();
    }

    record ProjectResponse(UUID id, UUID organizationId, String name, String description, String domainContext) {
        static ProjectResponse from(Project project) {
            return new ProjectResponse(project.getId(), project.getOrganizationId(), project.getName(), project.getDescription(), project.getDomainContext());
        }
    }
}
