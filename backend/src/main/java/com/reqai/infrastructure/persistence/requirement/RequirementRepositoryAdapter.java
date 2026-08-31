package com.reqai.infrastructure.persistence.requirement;

import com.reqai.application.requirement.RequirementRepository;
import com.reqai.domain.requirement.Requirement;
import org.springframework.stereotype.Repository;

@Repository
public class RequirementRepositoryAdapter implements RequirementRepository {
    private final JpaRequirementRepository repository;
    public RequirementRepositoryAdapter(JpaRequirementRepository repository) { this.repository = repository; }
    @Override public Requirement save(Requirement requirement) { return repository.save(requirement); }
}
