# ReqAI — Database Model

**Version:** 1.0  
**Database:** PostgreSQL

## 1. Modeling principles

- UUID identifiers for externally exposed entities.
- Foreign keys for ownership and traceability.
- Timestamps for auditability.
- Soft deletion only where justified by the domain.
- AI-generated content must be distinguishable from user-approved content.

## 2. Core entities

```text
User
  │
  └──< Project
          │
          └──< Requirement
                  │
                  ├──< RequirementVersion
                  │       ├──< UserStory
                  │       ├──< AcceptanceCriterion
                  │       ├──< BusinessRule
                  │       └──< ClarificationQuestion
                  │
                  └──< Analysis
                          └──< QualityFinding
```

## 3. Tables

### users

- id UUID PK
- email VARCHAR UNIQUE NOT NULL
- name VARCHAR NOT NULL
- created_at TIMESTAMPTZ NOT NULL
- updated_at TIMESTAMPTZ NOT NULL

### projects

- id UUID PK
- owner_id UUID FK users.id NOT NULL
- name VARCHAR NOT NULL
- description TEXT
- domain_context TEXT
- created_at TIMESTAMPTZ NOT NULL
- updated_at TIMESTAMPTZ NOT NULL

### requirements

- id UUID PK
- project_id UUID FK projects.id NOT NULL
- title VARCHAR
- source_text TEXT NOT NULL
- status VARCHAR NOT NULL
- created_at TIMESTAMPTZ NOT NULL
- updated_at TIMESTAMPTZ NOT NULL

### requirement_versions

- id UUID PK
- requirement_id UUID FK requirements.id NOT NULL
- version_number INTEGER NOT NULL
- source_text TEXT NOT NULL
- created_by VARCHAR NOT NULL
- created_at TIMESTAMPTZ NOT NULL

### user_stories

- id UUID PK
- requirement_version_id UUID FK requirement_versions.id NOT NULL
- actor TEXT NOT NULL
- goal TEXT NOT NULL
- benefit TEXT NOT NULL
- generated_by_ai BOOLEAN NOT NULL
- approved_at TIMESTAMPTZ

### acceptance_criteria

- id UUID PK
- requirement_version_id UUID FK requirement_versions.id NOT NULL
- sequence INTEGER NOT NULL
- description TEXT NOT NULL
- generated_by_ai BOOLEAN NOT NULL
- approved_at TIMESTAMPTZ

### business_rules

- id UUID PK
- requirement_version_id UUID FK requirement_versions.id NOT NULL
- code VARCHAR
- description TEXT NOT NULL
- status VARCHAR NOT NULL
- generated_by_ai BOOLEAN NOT NULL
- approved_at TIMESTAMPTZ

Suggested status values:

- PROPOSED
- CONFIRMED
- REJECTED

### clarification_questions

- id UUID PK
- requirement_version_id UUID FK requirement_versions.id NOT NULL
- question TEXT NOT NULL
- priority VARCHAR NOT NULL
- resolved BOOLEAN NOT NULL
- answer TEXT

### analyses

- id UUID PK
- requirement_version_id UUID FK requirement_versions.id NOT NULL
- provider VARCHAR NOT NULL
- model VARCHAR NOT NULL
- quality_score NUMERIC(5,2)
- status VARCHAR NOT NULL
- created_at TIMESTAMPTZ NOT NULL

### quality_findings

- id UUID PK
- analysis_id UUID FK analyses.id NOT NULL
- category VARCHAR NOT NULL
- severity VARCHAR NOT NULL
- message TEXT NOT NULL
- recommendation TEXT
- resolved BOOLEAN NOT NULL

## 4. Future RAG entities

The database may later include:

- documents
- document_chunks
- embeddings
- knowledge_sources

Embeddings will use pgvector.

## 5. Key indexes

Initial indexes should cover:

- projects.owner_id
- requirements.project_id
- requirement_versions.requirement_id
- analyses.requirement_version_id
- quality_findings.analysis_id

## 6. Traceability

The model deliberately connects:

```text
Project
  ↓
Requirement
  ↓
Requirement Version
  ├── User Story
  ├── Acceptance Criteria
  ├── Business Rules
  ├── Questions
  └── Analysis
       └── Findings
```

This allows future impact analysis and audit trails without redesigning the core domain.
