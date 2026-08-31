package com.reqai.application.requirement;

import com.reqai.domain.requirement.Requirement;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CreateRequirementUseCase {
    private final RequirementRepository repository;
    public CreateRequirementUseCase(RequirementRepository repository) { this.repository = repository; }
    public Requirement execute(UUID projectId, String title, String sourceText) {
        return repository.save(new Requirement(projectId, title, sourceText));
    }
}
