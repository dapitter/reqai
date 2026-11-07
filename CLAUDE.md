# ReqAI — Claude Code Project Guidance

## Product context
ReqAI is an AI-powered requirements engineering platform. It transforms informal business demands into clear, testable, traceable requirements. AI assists the analyst; the human remains responsible for business decisions and approval.

## Repository architecture
- Backend: Java 21, Spring Boot 3.5, Maven, PostgreSQL, Flyway.
- Database: PostgreSQL 17 with pgvector via Docker Compose.
- Frontend: Next.js 15, React 19, TypeScript.
- Product and architecture decisions: `docs/product/` and `docs/architecture/`.
- MVP backlog: `docs/requirements/mvp-backlog.md`.

Read the relevant architecture and product documents before changing application behavior. Do not introduce a second database or vector store for the MVP: PostgreSQL + pgvector is the documented decision.

## Requirements-engineering rules
1. Never invent business rules, field behavior, permissions, statuses, calculations, or integrations.
2. Separate confirmed facts, assumptions, recommendations, and open questions.
3. Every generated requirement must be traceable to its source document, section, or user input.
4. Preserve uncertainty. If evidence is missing or conflicting, create a pending question instead of guessing.
5. Acceptance criteria must be observable, testable, and unambiguous.
6. AI-generated business rules remain proposed until a human approves them.
7. Never overwrite approved requirement versions or source documents.
8. Keep generated artifacts editable and preserve their origin (AI-generated or human-authored).

## User Story output convention
When the user requests a Log.Lab-style User Story, use this structure:
- EU COMO
- DESEJO
- COM O OBJETIVO
- Protótipo/Tela (when applicable)
- CA1 — Navegação/título
- CA2 — Campos, filtros e grid
- CA3 — Regras e validações
- CA4 — Botões e ações
- CA5 — Observações e pendências
- RNF — Requisitos não funcionais

Do not fabricate a prototype, screen, field, validation, or rule. Mark missing details as `[A DEFINIR — PO/DEV]`.

## Working agreements
- Inspect existing code and tests before editing.
- Prefer small, focused changes that preserve the current architecture.
- Do not add dependencies, external services, secrets, or infrastructure without documenting the reason.
- Do not run destructive database commands or rewrite migration history.
- Database changes must be additive Flyway migrations.
- Add or update tests for behavior changes.
- Do not claim tests passed unless they were actually executed.
- Never commit API keys, credentials, personal data, or production documents.

## Current implementation focus
Evolve the existing requirement-generation workflow with Claude Code and a hybrid retrieval strategy:
1. Establish repeatable requirement-analysis and User Story-generation workflows.
2. Add document ingestion and source metadata.
3. Implement lexical + vector retrieval with PostgreSQL/pgvector.
4. Build traceable context assembly and structured generation.
5. Evaluate quality against curated requirement examples.

## Useful commands
Backend:
```bash
cd backend
./mvnw test
# If the Maven wrapper is not present:
mvn test
```

Database:
```bash
docker compose up -d postgres
```

Frontend:
```bash
cd frontend
npm install
npm run dev
```

Check the actual repository scripts and environment before assuming a command is available.
