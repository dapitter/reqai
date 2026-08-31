# ReqAI — AI Architecture

**Version:** 1.1  
**Status:** Foundation

## 1. Objective

The AI layer must convert natural-language requirements into structured artifacts while preserving uncertainty and making generated assumptions explicit.

## 2. AI pipeline

```text
User Input
    ↓
Input Validation
    ↓
Project Context
    ↓
Optional RAG Retrieval
    ↓
Prompt Construction
    ↓
Provider Resolution (BYOK / Managed)
    ↓
LLM Provider
    ↓
Structured Output
    ↓
Schema Validation
    ↓
Domain Validation
    ↓
Analysis Persistence
    ↓
UI Result
```

## 3. BYOK — Bring Your Own Key

ReqAI will support user-owned AI provider credentials.

The user can configure a provider and API key from a secure settings area. The key is handled exclusively by the backend and is never sent back to the browser in plaintext.

Conceptual flow:

```text
User
 ↓ HTTPS
Provider Configuration API
 ↓
Encrypted Secret Storage
 ↓
AI Gateway
 ↓
Provider Adapter
 ↓
LLM
```

The AI Gateway must select the credential source at runtime without exposing provider-specific credential handling to the domain layer.

Supported provider adapters can evolve independently, for example:

```text
AIProvider
├── OpenAIAdapter
├── AnthropicAdapter
├── GeminiAdapter
└── FutureAdapter
```

## 4. First AI capabilities

### Generate User Story

Input:

- requirement text
- project context

Output:

- title
- actor
- goal
- benefit

### Generate Acceptance Criteria

Output:

- ordered criteria
- observable behavior
- relevant conditions

### Generate Business Rules

Output:

- proposed rules
- confidence/context classification
- unresolved assumptions

### Analyze Quality

Output:

- quality score
- category
- severity
- explanation
- recommendation

### Generate Clarification Questions

Output:

- question
- priority
- reason

## 5. Prompt design principles

Prompts should instruct the model to:

1. Use only supplied project context.
2. Separate facts from assumptions.
3. Never fabricate a business rule as confirmed behavior.
4. Prefer precise, testable language.
5. Return the requested structured schema.
6. Identify missing information explicitly.

## 6. Structured output

The application should use typed schemas for AI responses.

Conceptual example:

```json
{
  "userStory": {
    "title": "Accept available ride",
    "actor": "Driver",
    "goal": "accept an available ride",
    "benefit": "start serving the request"
  },
  "acceptanceCriteria": [],
  "businessRules": [],
  "qualityFindings": [],
  "questions": []
}
```

The exact provider-specific implementation is isolated behind the AI Gateway.

## 7. Hallucination controls

The system will reduce unsupported generation by:

- providing project context;
- requiring explicit assumptions;
- validating output schemas;
- distinguishing proposed from confirmed rules;
- allowing human review before approval;
- preserving source text alongside generated artifacts;
- tracing RAG context to source documents.

## 8. RAG evolution

ReqAI supports a vector-store abstraction.

### Default

PostgreSQL + **pgvector** is the initial vector-search implementation.

```text
Documents
    ↓
Text extraction
    ↓
Chunking
    ↓
Embeddings
    ↓
PostgreSQL + pgvector
    ↓
Semantic retrieval
    ↓
Relevant context
    ↓
LLM
```

### Alternative

**Qdrant** can be enabled as a dedicated vector store when scale or retrieval requirements justify it.

```text
VectorStore
├── PgVectorStore
└── QdrantVectorStore
```

The retrieved context should always be traceable to its source document and chunk.

## 9. Agent evolution

The future Requirements Analyst Agent may orchestrate specialized capabilities:

```text
Requirements Agent
       │
       ├── Context Retrieval
       ├── Requirement Analyzer
       ├── Quality Analyzer
       ├── Impact Analyzer
       ├── Specification Generator
       └── Browser Agent
```

Agents are not part of the first MVP, but the architecture reserves a clear boundary for them.

## 10. Agent Browser

The Agent Browser is an isolated tool/service used by agents that need controlled browser interaction.

Potential uses:

- navigating authorized web applications;
- validating UI workflows;
- collecting information from systems without a suitable API;
- executing controlled browser actions.

It must operate with explicit policies and tool permissions. Sensitive actions require user authorization.

Browser execution must be isolated from the main API process and must not receive unrestricted access to arbitrary systems.

## 11. AI execution modes

### Managed mode

A future managed plan can use platform-controlled provider credentials.

### BYOK mode

The user supplies their own provider credential and pays the provider directly.

The architecture should support both modes through the same AI Gateway.

## 12. Observability

AI telemetry should capture:

- provider
- model
- latency
- token usage
- request status
- structured validation result
- error category
- retrieval metadata when RAG is used

Sensitive user content and secrets should not be logged unnecessarily.
