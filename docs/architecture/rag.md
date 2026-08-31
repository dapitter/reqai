# ReqAI — RAG Architecture

## MVP decision

PostgreSQL + pgvector is the only vector store required for the MVP.

## Pipeline

```text
Document
  ↓
Extraction
  ↓
Chunking
  ↓
Embedding Provider
  ↓
PostgreSQL / pgvector
  ↓
Similarity Search
  ↓
Context Builder
  ↓
AI Gateway
  ↓
Structured Requirement Analysis
```

## Retrieval principles

- Retrieval is scoped by organization and project.
- Top-K must be configurable.
- Similarity threshold must be configurable.
- Source document and chunk identifiers must be retained.
- Retrieved context must be inspectable for traceability.
- The model must distinguish retrieved facts from generated suggestions.

## Embeddings

Embedding provider selection must remain behind the AI Gateway abstraction. The database layer must not depend on a provider-specific SDK.

## Qdrant

Qdrant remains a future optional vector-store adapter. The domain and retrieval interfaces should avoid coupling to PostgreSQL-specific query semantics so a second implementation can be introduced if needed.
