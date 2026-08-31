package com.reqai.application.requirement;

import com.reqai.domain.requirement.Requirement;

public interface RequirementRepository {
    Requirement save(Requirement requirement);
}
