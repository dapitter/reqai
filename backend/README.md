# ReqAI Backend

Spring Boot backend for the ReqAI platform.

## Responsibilities

- REST API
- Application use cases
- Domain rules
- PostgreSQL persistence
- AI Gateway
- Authentication and authorization
- RAG orchestration

## Planned structure

```text
backend/
└── src/
    ├── main/java/com/reqai/
    │   ├── ReqAiApplication.java
    │   ├── domain/
    │   │   ├── project/
    │   │   ├── requirement/
    │   │   └── analysis/
    │   ├── application/
    │   ├── infrastructure/
    │   │   ├── persistence/
    │   │   ├── ai/
    │   │   └── configuration/
    │   └── presentation/
    └── test/java/com/reqai/
```

Implementation starts with US-001: Create Project.
