package com.reqai.domain.requirement;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "requirements")
public class Requirement {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="project_id", nullable=false) private UUID projectId;
    @Column(length=255) private String title;
    @Column(name="source_text", nullable=false, columnDefinition="TEXT") private String sourceText;
    @Column(nullable=false, length=40) private String status = "DRAFT";

    protected Requirement() {}
    public Requirement(UUID projectId, String title, String sourceText) {
        this.projectId = projectId; this.title = title; this.sourceText = sourceText;
    }
    public UUID getId(){ return id; }
    public UUID getProjectId(){ return projectId; }
    public String getTitle(){ return title; }
    public String getSourceText(){ return sourceText; }
    public String getStatus(){ return status; }
}
