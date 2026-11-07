# ReqAI — Claude + Hybrid RAG Implementation Plan

**Status:** Phase 1 — Claude Code operating layer
**Repository:** `dapitter/reqai`
**Execution choice:** Claude in VS Code
**Retrieval choice:** Hybrid lexical + vector retrieval
**Vector store:** PostgreSQL + pgvector (existing decision)

## 1. Goal

Evolve the existing ReqAI repository and its requirement/User Story generation workflow. Claude Code is the development and assisted-analysis interface in the first increment. The application architecture remains provider-neutral and PostgreSQL + pgvector remains the planned RAG store.

This is an incremental evolution, not a replacement project.

## 2. Current repository baseline

- Backend: Java 21, Spring Boot 3.5, Maven.
- Frontend: Next.js 15, React 19, TypeScript.
- Database: PostgreSQL 17 with pgvector in Docker Compose.
- Migrations: Flyway under `backend/src/main/resources/db/migration`.
- AI abstractions already exist: `AIProvider` and `AIProviderRegistry`.
- AI/RAG architecture documents already exist under `docs/architecture/`.
- Existing product scope and backlog are defined under `docs/product/` and `docs/requirements/`.

## 3. Phases

### Phase 0 — Baseline audit
- Inventory current backend, frontend, migrations, and architecture decisions.
- Identify inconsistencies between documentation and implementation.
- Preserve existing AI provider abstractions and PostgreSQL + pgvector decision.
- Do not rewrite migration history.

**Known finding to resolve before enabling RAG:** the repository currently contains a `requirement_versions` table creation in both `V1__initial_schema.sql` and `V2__add_requirement_version.sql`. A fresh Flyway migration run may fail on the duplicate table. Confirm the actual database history before preparing an additive corrective migration; never edit migrations that may already have been applied.

### Phase 1 — Claude Code operating layer (current)
- Add concise repository instructions in root `CLAUDE.md`.
- Add reusable skills for requirement analysis, User Story generation, and User Story validation.
- Enforce evidence, source citations, uncertainty handling, and human approval.
- Validate the skills manually in Claude Code against a small set of existing requirements.

**Exit criteria**
- Claude consistently reads product and architecture context.
- Generated stories follow the agreed Log.Lab structure when requested.
- Every material business rule is sourced or explicitly marked as pending.
- Validation reports identify unsupported claims and unresolved decisions.

### Phase 2 — Document ingestion
- Define supported input types and size limits.
- Add document metadata, versioning, content hash, processing status, and project/tenant ownership.
- Extract text from Markdown and text-based documents first.
- Add PDF/DOCX/XLSX extraction in isolated adapters with tests.
- Split text into chunks while preserving headings, page/sheet, source path, and offsets.
- Make ingestion idempotent using content hashes.
- Store original files or references separately from extracted chunks.

### Phase 3 — Hybrid retrieval
- Keep lexical and vector retrieval as separate adapters.
- Lexical retrieval: PostgreSQL full-text search (tsvector/tsquery) or equivalent indexed search.
- Vector retrieval: pgvector cosine/distance search.
- Apply organization and project filters before ranking.
- Fuse rankings using Reciprocal Rank Fusion (RRF) or a measured alternative.
- Make top-K and thresholds configurable.
- Return source document, chunk, heading/page, and ranking metadata.
- Add retrieval tests for exact identifiers, synonyms, and semantic paraphrases.

### Phase 4 — Context assembly and generation
- Build a bounded context package from retrieved chunks.
- Deduplicate overlapping chunks and prioritize approved/current sources.
- Include source metadata in the model context.
- Generate structured User Story, acceptance criteria, proposed rules, quality findings, and clarification questions.
- Validate JSON/schema and domain rules before persistence.
- Preserve proposed vs. confirmed rules and AI vs. human authorship.
- Require human review before approval.

### Phase 5 — Evaluation and hardening
- Create a curated benchmark of real or anonymized requirements.
- Measure retrieval precision/recall, source attribution, unsupported-claim rate, completeness, testability, and schema validity.
- Add regression tests for prompt and model changes.
- Add latency, token/cost, provider, and retrieval telemetry without logging secrets or unnecessary sensitive content.
- Add security tests for cross-project/tenant data isolation and prompt injection in retrieved documents.

## 4. Target runtime architecture

```text
Documents / Existing User Stories / Business Rules
                    |
             Ingestion Pipeline
                    |
       Extraction -> Normalization -> Chunking
                    |
             PostgreSQL
          + metadata + tsvector
          + pgvector embeddings
                    |
         Hybrid Retrieval Service
          |                  |
     Lexical Search     Vector Search
          |                  |
          +---- Rank Fusion -+
                    |
             Context Builder
                    |
              AI Gateway
                    |
       Claude provider adapter (initial)
                    |
       Structured output + validation
                    |
           Human review / approval
```

Claude Code skills in Phase 1 guide repository-level analysis. The production ReqAI runtime integration is a separate implementation concern and must not assume that the local Claude Code session is an application API.

## 5. Key design decisions

1. PostgreSQL + pgvector remains the MVP vector store.
2. Hybrid means lexical and vector retrieval are both executed and their rankings are combined; it is not merely a fallback from one to the other.
3. Retrieval is always scoped by tenant/organization and project.
4. Every chunk retains source identity and location metadata.
5. Retrieved documents are untrusted data, not executable instructions.
6. No autonomous approval or publication of generated User Stories.
7. Provider credentials remain server-side and outside Git.
8. Embedding dimensions must be selected/configured per embedding model; do not hard-code an unverified dimension.
9. Database migrations are append-only after release.

## 6. Initial validation scenarios

- Exact identifier lookup (e.g. a User Story ID) returns the matching source.
- A paraphrased business rule retrieves the same relevant source semantically.
- A query scoped to Project A never returns Project B content.
- A conflicting source is reported rather than silently resolved.
- A missing business rule becomes a clarification question.
- Generated criteria are testable and traceable.
- Prompt injection embedded in a document does not override system/repository instructions.

## 7. Open decisions before runtime RAG implementation

- Which embedding provider/model will be used for Portuguese documents?
- What embedding dimensions and distance metric does that model require?
- Which formats are required in the first ingestion increment?
- Where will original documents be stored (local filesystem, object storage, or existing document store)?
- What are the retention and deletion rules for source documents and embeddings?
- Is Claude to be called through the user's Claude Code session only for development, or should the deployed ReqAI application also call Anthropic through the AI Gateway?

These decisions must be recorded before adding provider-specific dependencies or schema assumptions.
