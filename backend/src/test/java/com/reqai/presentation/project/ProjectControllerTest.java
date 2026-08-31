package com.reqai.presentation.project;

import com.reqai.application.project.CreateProjectUseCase;
import com.reqai.domain.project.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CreateProjectUseCase createProjectUseCase;

    @Test
    void shouldCreateProject() throws Exception {
        UUID organizationId = UUID.randomUUID();
        Project project = new Project(organizationId, "ReqAI", "Requirements platform", "Software");

        when(createProjectUseCase.execute(eq(organizationId), eq("ReqAI"), eq("Requirements platform"), eq("Software")))
                .thenReturn(project);

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "organizationId": "%s",
                                  "name": "ReqAI",
                                  "description": "Requirements platform",
                                  "domainContext": "Software"
                                }
                                """.formatted(organizationId)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldRejectBlankName() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "organizationId": "%s",
                                  "name": ""
                                }
                                """.formatted(UUID.randomUUID())))
                .andExpect(status().isBadRequest());
    }
}
