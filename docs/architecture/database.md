# ReqAI — Database Architecture

**Version:** 1.1  
**Database:** PostgreSQL  
**Vector search:** pgvector

## 1. Decision

ReqAI will use **PostgreSQL + pgvector** as the primary persistence and vector-search solution.

Qdrant is intentionally excluded from the MVP runtime. It remains a future alternative if scale or retrieval requirements justify a dedicated vector database.

## 2. Why PostgreSQL + pgvector

- One primary database for transactional and vector data.
- Lower operational complexity.
- Strong relational modeling for requirements and traceability.
- PostgreSQL transactions for domain consistency.
- Vector similarity search without another mandatory database.
- Simple local development with Docker Compose.
- Strong foundation for RAG.

## 3. Core entities

```text
User
  │
  └──< OrganizationMember >── Organization
                                  │
                                  └──< Project
                                          │
                                          ├──< Requirement
                                          │       │
                                          │       └──< RequirementVersion
                                          │                 ├──< UserStory
                                          │                 ├──< AcceptanceCriterion
                                          │                 ├──< BusinessRule
                                          │                 ├──< QualityFinding
                                          │                 └──< ClarificationQuestion
                                          │
                                          ├──< Document
                                          │       │
                                          │       └──< DocumentChunk
                                          │                  └── embedding vector
                                          │
                                          └──< AIAnalysis
```

## 4. Main tables

### users

Application users.

### organizations

Tenant boundary for future multi-tenant operation.

### organization_members

Relationship between users and organizations, including role.

### projects

Product/project context used during requirements analysis.

### requirements

Stable identity of a requirement across versions.

### requirement_versions

Immutable snapshots of requirement content and approval state.

### user_stories

Structured User Story generated or edited from a requirement version.

### acceptance_criteria

Testable acceptance criteria associated with a User Story.

### business_rules

Business rules associated with the requirement. Proposed AI rules must be distinguishable from confirmed rules.

### quality_findings

Quality issues detected during analysis.

### clarification_questions

Questions generated from missing or ambiguous information.

### ai_analyses

Execution record for an AI analysis, including provider/model metadata and structured result status.

### documents

Project documents that can provide context for RAG.

### document_chunks

Chunked document content with embeddings for semantic retrieval.

## 5. Vector model

`document_chunks.embedding` will use PostgreSQL's `vector` type provided by pgvector.

The embedding dimension must be configurable because it depends on the selected embedding model. Provider-specific dimensions must not leak into the domain model.

## 6. RAG query flow

```text
Requirement
    ↓
Create query embedding
    ↓
pgvector similarity search
    ↓
Top-K relevant chunks
    ↓
Build context
    ↓
LLM
    ↓
Structured response
```

## 7. Traceability

Every retrieved chunk must retain its source document and project association. AI analyses should preserve enough metadata to identify the context used for the execution.

## 8. Data ownership

All project-scoped entities must be reachable through an organization/project ownership boundary. Repository services must enforce authorization before returning or mutating project data.

## 9. Versioning strategy

Requirements have a stable identity and multiple versions.

```text
Requirement
   │
   ├── Version 1 — Draft
   ├── Version 2 — AI refined
   ├── Version 3 — User edited
   └── Version 4 — Approved
```

Historical versions are never overwritten.

## 10. Future optimization

The MVP will start with straightforward pgvector similarity queries. Index strategy and retrieval parameters will be optimized from measured workload rather than prematurely adding infrastructure.
