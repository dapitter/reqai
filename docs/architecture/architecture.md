# ReqAI — Technical Architecture

**Version:** 1.1  
**Status:** Foundation

## 1. Architectural goal

ReqAI will use a modular architecture that separates product presentation, application use cases, domain rules, persistence, AI provider integration, agent capabilities, and infrastructure.

The architecture must support the MVP without prematurely introducing unnecessary microservices, while leaving clear boundaries for RAG, browser automation, and AI agents.

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
              ┌────────────────────────────────────────┐
              │          Spring Boot Backend           │
              │                                        │
              │ Presentation → Application → Domain    │
              │                    │                   │
              │                    ├── Persistence     │
              │                    ├── AI Gateway      │
              │                    ├── RAG Service     │
              │                    └── Agent Service   │
              └────────────┬───────────────┬───────────┘
                           │               │
              ┌────────────┘               └────────────────┐
              ▼                                             ▼
      ┌─────────────────┐                          ┌──────────────────┐
      │   PostgreSQL    │                          │   AI Providers   │
      │   + pgvector    │                          │ OpenAI / others  │
      └────────┬────────┘                          └──────────────────┘
               │
               │ optional RAG backend
               ▼
        ┌───────────────┐
        │     Qdrant    │
        │ vector search │
        └───────────────┘

                    Agent Browser
                         │
                         ▼
                 ┌───────────────┐
                 │ Browser Agent │
                 │ isolated svc  │
                 └───────────────┘
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
- AI provider / BYOK configuration
- Agent execution status
- API consumption

The frontend must never receive or persist raw AI provider secrets in browser storage.

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
│   ├── provider
│   ├── agent
│   └── shared
├── application
│   ├── project
│   ├── requirement
│   ├── analysis
│   ├── provider
│   └── agent
├── infrastructure
│   ├── persistence
│   ├── ai
│   ├── vector
│   ├── browser
│   └── configuration
└── presentation
    ├── project
    ├── requirement
    ├── analysis
    ├── provider
    └── agent
```

## 5. BYOK — Bring Your Own Key

BYOK is a first-class architecture capability for AI provider credentials.

The user may configure their own provider API key so ReqAI can execute AI operations using the user's provider account rather than a shared platform key.

Rules:

- API keys are never stored in plaintext in the database.
- Keys are submitted only over HTTPS.
- The backend is the only component allowed to use provider credentials.
- Keys should be encrypted at rest using an application-managed encryption mechanism or external secret manager.
- Logs must never contain API keys.
- The UI should display only masked credentials.
- Provider selection and model selection are stored as configuration metadata, not as secrets.
- A platform-managed provider key may exist for a future managed plan, but BYOK remains supported independently.

Conceptual flow:

```text
User
 ↓
Provider Settings
 ↓ HTTPS
Backend
 ↓
Secret Encryption / Secret Store
 ↓
AI Gateway
 ↓
Selected Provider + Model
```

## 6. Application flow

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
       Retrieve relevant context
                 │
                 ▼
       Resolve AI provider
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

## 7. AI Gateway

The application will isolate the LLM provider behind an internal abstraction.

Example conceptual contract:

```text
AIAnalysisService
 ├── analyzeRequirement()
 ├── generateUserStory()
 ├── generateAcceptanceCriteria()
 └── generateBusinessRules()
```

The gateway resolves the provider configuration without exposing provider-specific concerns to the domain.

## 8. Vector search — pgvector and Qdrant

ReqAI supports two architectural vector-search strategies.

### Default for MVP / early production

**PostgreSQL + pgvector**.

Advantages:

- One primary persistence platform.
- Transactional metadata and vectors can remain close together.
- Lower operational complexity.
- Good fit for the initial product scale.

### Optional scale-out strategy

**Qdrant** may be introduced when semantic search volume, retrieval requirements, or operational characteristics justify a dedicated vector database.

The application should hide this decision behind a `VectorStore` abstraction:

```text
VectorStore
 ├── PgVectorStore
 └── QdrantVectorStore
```

This means the product can start with pgvector and migrate or route selected workloads to Qdrant without changing domain logic.

## 9. Agent Browser

The Agent Browser is an isolated capability for future browser-based agents.

Potential uses include:

- navigating external web applications;
- collecting authorized project information;
- validating UI flows;
- executing controlled browser tasks;
- supporting integrations where an API is unavailable.

Security requirements:

- Browser execution must be isolated from the main API process.
- Credentials must be handled through secure secret storage.
- Navigation and actions must be allowlisted or policy-controlled where appropriate.
- Browser sessions must be auditable.
- Agent actions require explicit user authorization for sensitive operations.
- No unrestricted autonomous access to arbitrary systems.

The Agent Browser is **not required for the MVP**. It belongs to the agent evolution phase.

## 10. Docker Compose

Docker Compose will provide the reproducible local development environment.

Initial services:

```text
services:
  postgres
  backend
  frontend
```

When RAG is enabled locally:

```text
services:
  postgres
  qdrant        # optional profile
  backend
  frontend
```

When browser agents are enabled:

```text
services:
  postgres
  qdrant
  backend
  frontend
  browser-agent
```

Compose profiles should prevent optional infrastructure from being required by the MVP.

## 11. Hostinger infrastructure

Hostinger is the target infrastructure for the self-hosted backend environment where compatible with the selected hosting product.

Target deployment concept:

```text
Internet
   │
   ▼
HTTPS / Reverse Proxy
   │
   ├───────────────► Next.js
   │
   └───────────────► Spring Boot API
                         │
                         ├── PostgreSQL
                         ├── pgvector
                         ├── Qdrant (optional)
                         ├── AI Providers
                         └── Browser Agent (optional)
```

Docker Compose is the deployment unit for services that are hosted together on a Hostinger environment capable of running Docker workloads.

Infrastructure secrets must be injected through the hosting environment and must not be committed to Git.

## 12. Security boundaries

- Secrets stored exclusively in encrypted storage, environment variables, or deployment secret stores.
- Authentication handled at the application boundary.
- Authorization checked for every project resource.
- AI provider keys never exposed to the browser.
- User-generated content treated as untrusted input.
- Agent Browser isolated from the main application process.
- Logs must not expose API keys or sensitive request content unnecessarily.

## 13. Error handling

The API should expose stable application error codes rather than leaking infrastructure exceptions.

Example:

```json
{
  "code": "AI_PROVIDER_UNAVAILABLE",
  "message": "The AI analysis service is temporarily unavailable.",
  "traceId": "..."
}
```

## 14. Testing strategy

### Unit tests

Domain rules and application services.

### Integration tests

Spring Boot integration with PostgreSQL using Testcontainers.

### AI contract tests

Validate parsing and schema compatibility using deterministic fixtures.

### RAG tests

Validate chunking, embedding metadata, retrieval relevance, and source traceability.

### Agent tests

Validate agent policies, tool permissions, browser action boundaries, and failure handling.

### End-to-end tests

Playwright covering the primary user journey.

## 15. Deployment evolution

### Local development

```text
Docker Compose
├── Next.js
├── Spring Boot
├── PostgreSQL + pgvector
├── Qdrant (optional)
└── Browser Agent (optional)
```

### Initial hosted environment

```text
Hostinger
├── Reverse Proxy / HTTPS
├── Next.js
├── Spring Boot
├── PostgreSQL
├── pgvector
└── optional Qdrant / Browser Agent
```

### Future scale

```text
Next.js
   ↓
API
   ↓
Application Core
   ├── PostgreSQL
   ├── VectorStore
   ├── AI Gateway
   ├── Document Pipeline
   ├── Agent Orchestrator
   └── Browser Agent
```

## 16. Architectural decisions

The initial implementation will be a **modular monolith** on the backend rather than microservices.

Reason: the MVP needs strong domain boundaries and testability, but does not yet have operational complexity that justifies distributed deployment.

Vector search starts with **pgvector**. Qdrant remains an explicit adapter for future scale or specialized retrieval workloads.

BYOK is part of the product architecture from the beginning, even if the first MVP exposes only a limited provider configuration flow.

Docker Compose is the standard local orchestration mechanism.

Hostinger is the target self-hosted infrastructure for the deployed portfolio/product environment, subject to the selected Hostinger plan supporting the required Docker workloads.
