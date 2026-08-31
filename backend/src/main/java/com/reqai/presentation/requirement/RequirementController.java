package com.reqai.presentation.requirement;

import com.reqai.application.requirement.CreateRequirementUseCase;
import com.reqai.domain.requirement.Requirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/requirements")
public class RequirementController {
    private final CreateRequirementUseCase useCase;
    public RequirementController(CreateRequirementUseCase useCase) { this.useCase = useCase; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequirementResponse create(@Valid @RequestBody CreateRequirementRequest request) {
        return RequirementResponse.from(useCase.execute(request.projectId(), request.title(), request.sourceText()));
    }

    public record CreateRequirementRequest(
            @NotNull UUID projectId,
            @Size(max=255) String title,
            @NotBlank String sourceText) {}

    public record RequirementResponse(UUID id, UUID projectId, String title, String sourceText, String status) {
        static RequirementResponse from(Requirement r) {
            return new RequirementResponse(r.getId(), r.getProjectId(), r.getTitle(), r.getSourceText(), r.getStatus());
        }
    }
}
