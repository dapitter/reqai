package com.reqai.application.project;

import com.reqai.domain.project.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectQueryRepository {
    List<Project> findByOrganizationId(UUID organizationId);
}
