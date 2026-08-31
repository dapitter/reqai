package com.reqai.domain.project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "domain_context", columnDefinition = "TEXT")
    private String domainContext;

    protected Project() {
    }

    public Project(UUID organizationId, String name, String description, String domainContext) {
        this.organizationId = organizationId;
        this.name = name;
        this.description = description;
        this.domainContext = domainContext;
    }

    public UUID getId() { return id; }
    public UUID getOrganizationId() { return organizationId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDomainContext() { return domainContext; }
}
