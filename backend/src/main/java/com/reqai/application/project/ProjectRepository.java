package com.reqai.application.project;

import com.reqai.domain.project.Project;

public interface ProjectRepository {
    Project save(Project project);
}
