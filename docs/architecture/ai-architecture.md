# ReqAI — AI Architecture

**Version:** 1.0  
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
Prompt Construction
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

## 3. First AI capabilities

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

## 4. Prompt design principles

Prompts should instruct the model to:

1. Use only supplied project context.
2. Separate facts from assumptions.
3. Never fabricate a business rule as confirmed behavior.
4. Prefer precise, testable language.
5. Return the requested structured schema.
6. Identify missing information explicitly.

## 5. Structured output

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

The exact provider-specific implementation is intentionally isolated behind the AI Gateway.

## 6. Hallucination controls

The system will reduce unsupported generation by:

- providing project context;
- requiring explicit assumptions;
- validating output schemas;
- distinguishing proposed from confirmed rules;
- allowing human review before approval;
- preserving source text alongside generated artifacts.

## 7. RAG evolution

When documents are introduced:

```text
Documents
    ↓
Text extraction
    ↓
Chunking
    ↓
Embeddings
    ↓
pgvector
    ↓
Semantic retrieval
    ↓
Relevant context
    ↓
LLM
```

The retrieved context should be traceable to its source document and chunk.

## 8. Agent evolution

The future Requirements Analyst Agent may orchestrate specialized capabilities:

```text
Requirements Agent
       │
       ├── Context Retrieval
       ├── Requirement Analyzer
       ├── Quality Analyzer
       ├── Impact Analyzer
       └── Specification Generator
```

Agents are not part of the MVP implementation.

## 9. Observability

Future AI telemetry should capture:

- provider
- model
- latency
- token usage
- request status
- structured validation result
- error category

Sensitive user content should not be logged unnecessarily.
