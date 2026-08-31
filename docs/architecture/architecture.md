# ReqAI — Technical Architecture

**Version:** 1.0  
**Status:** Foundation

## 1. Architectural goal

ReqAI will use a modular architecture that separates product presentation, application use cases, domain rules, persistence, and AI provider integration.

The architecture must support the MVP without prematurely introducing unnecessary microservices, while leaving clear boundaries for future RAG and agent capabilities.

## 2. High-level architecture

```text
                        ┌──────────────────────┐
                        │       Browser        │
                        └──────────┬───────────┘
                                   │ HTTPS
                                   ▼
                        ┌──────────────────────┐
                        │      Next.js Web     │
                        │       Frontend       │
                        └──────────┬───────────┘
                                   │ REST/JSON
                                   ▼
                 ┌─────────────────────────────────┐
                 │       Spring Boot Backend       │
                 │                                 │
                 │  Presentation                  │
                 │       ↓                         │
                 │  Application                   │
                 │       ↓                         │
                 │  Domain                         │
                 │       ↓                         │
                 │  Infrastructure                │
                 └──────────┬───────────┬──────────┘
                            │           │
                            │           ▼
                            │    ┌───────────────┐
                            │    │   AI Gateway  │
                            │    └───────┬───────┘
                            │            │
                            │            ▼
                            │      LLM Provider
                            │
                            ▼
                    ┌─────────────────┐
                    │   PostgreSQL    │
                    │   + pgvector   │
                    └─────────────────┘
```

## 3. Frontend

Technology: Next.js + TypeScript.

Responsibilities:

- Authentication UI
- Project management
- Requirement editor
- AI analysis experience
- Generated artifact editor
- Quality findings visualization
- Requirement history
- API consumption

The frontend must not contain AI provider credentials or business-critical rules.

## 4. Backend

Technology: Java + Spring Boot.

The backend is the system boundary for domain operations.

Suggested package organization:

```text
com.reqai
├── ReqAiApplication
├── domain
│   ├── project
│   ├── requirement
│   ├── analysis
│   └── shared
├── application
│   ├── project
│   ├── requirement
│   └── analysis
├── infrastructure
│   ├── persistence
│   ├── ai
│   └── configuration
└── presentation
    ├── project
    ├── requirement
    └── analysis
```

## 5. Application flow

```text
POST /api/v1/projects/{projectId}/requirements/analyze
                 │
                 ▼
        Validate request
                 │
                 ▼
       Load project context
                 │
                 ▼
       Build AI analysis input
                 │
                 ▼
          AI Gateway
                 │
                 ▼
       Validate AI output
                 │
                 ▼
        Persist analysis
                 │
                 ▼
           Return result
```

## 6. AI Gateway

The application will isolate the LLM provider behind an internal abstraction.

Example conceptual contract:

```text
AIAnalysisService
 ├── analyzeRequirement()
 ├── generateUserStory()
 ├── generateAcceptanceCriteria()
 └── generateBusinessRules()
```

This prevents domain/application code from depending directly on a specific provider SDK.

## 7. AI output validation

AI output must be treated as untrusted generated data.

The backend will:

1. Request structured output.
2. Parse the response.
3. Validate the expected schema.
4. Reject malformed responses.
5. Mark generated content as AI-generated.
6. Persist the result only after successful validation.

## 8. RAG readiness

The MVP does not require a complete RAG pipeline, but the architecture will reserve an infrastructure boundary for:

- document ingestion;
- chunking;
- embeddings;
- vector search;
- context assembly.

PostgreSQL + pgvector is preferred to avoid introducing a second database during the early product stages.

## 9. Security boundaries

- Secrets stored exclusively in environment variables or deployment secret stores.
- Authentication handled at the application boundary.
- Authorization checked for every project resource.
- AI provider keys never exposed to the browser.
- User-generated content treated as untrusted input.
- Logs must not expose API keys or sensitive request content unnecessarily.

## 10. Error handling

The API should expose stable application error codes rather than leaking infrastructure exceptions.

Example:

```json
{
  "code": "AI_PROVIDER_UNAVAILABLE",
  "message": "The AI analysis service is temporarily unavailable.",
  "traceId": "..."
}
```

## 11. Testing strategy

### Unit tests

Domain rules and application services.

### Integration tests

Spring Boot integration with PostgreSQL using Testcontainers.

### AI contract tests

Validate parsing and schema compatibility using deterministic fixtures.

### End-to-end tests

Playwright covering the primary user journey.

## 12. Deployment evolution

### MVP

```text
Next.js → Backend → PostgreSQL
                  ↓
                 LLM
```

### Future

```text
Next.js
   ↓
API
   ↓
Application Core
   ├── PostgreSQL
   ├── Vector Search
   ├── AI Gateway
   ├── Document Pipeline
   └── Agent Orchestrator
```

## 13. Architectural decision

The initial implementation will be a **modular monolith** on the backend rather than microservices.

Reason: the MVP needs strong domain boundaries and testability, but does not yet have operational complexity that justifies distributed deployment. Modules can be extracted later if real product requirements demand it.
