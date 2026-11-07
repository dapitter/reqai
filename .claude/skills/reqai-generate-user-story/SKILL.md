---
name: reqai-generate-user-story
description: Generate a complete, traceable User Story in the ReqAI/Log.Lab format from a requirement and verified project context. Use when the user asks to create, write, or refine a User Story.
---

# ReqAI — User Story Generation

## Preconditions
- Identify the target project/module and source demand.
- Search existing requirements, User Stories, approved business rules, decisions, and relevant screen prototypes.
- Use lexical retrieval and, when available, semantic retrieval through the ReqAI hybrid RAG service.
- If required information is missing, either ask focused clarification questions or create an explicitly incomplete draft with pending decisions. Never fill gaps with invented behavior.

## Required structure
Use the following structure for Log.Lab-style User Stories:

### Identificação
- ID: only when supplied by the source system; otherwise `[A DEFINIR]`
- Título: concise verb + object
- Módulo/Funcionalidade: evidence-based value

### História
**EU COMO:** [actor/persona]

**DESEJO:** [capability or action]

**COM O OBJETIVO:** [business value]

### Protótipo/Tela
- Reference the actual prototype, screen, or component when available.
- Describe only evidenced UI behavior.
- If unavailable, write `[A DEFINIR — PO/UX]`.

### Critérios de Aceite
- **CA1 — Navegação e título:** route, access, page/modal title, and entry conditions where evidenced.
- **CA2 — Campos, filtros e grid:** fields, types, requiredness, masks, filters, columns, sorting, and empty states where evidenced.
- **CA3 — Regras e validações:** business rules, field validation, permissions, state transitions, and error handling.
- **CA4 — Botões e ações:** action, preconditions, result, confirmation, feedback, and cancellation behavior.
- **CA5 — Observações e pendências:** integrations, dependencies, edge cases, unresolved decisions, and explicit owners when known.

### RNF — Requisitos Não Funcionais
Include only applicable and evidenced security, privacy, accessibility, performance, availability, auditability, compatibility, and observability requirements. Mark unknown targets as pending rather than inventing numeric thresholds.

### Rastreabilidade
For each material rule or criterion, reference the source path and section, requirement ID, decision, or prototype. Distinguish confirmed sources from proposed content.

## Writing standards
- Use precise, testable language.
- Prefer one behavior per criterion.
- State preconditions and expected outcomes.
- Include positive, negative, boundary, and exception scenarios when supported.
- Avoid implementation decisions unless the source requires them.
- Do not use vague terms such as "properly", "quickly", or "user-friendly" without measurable meaning.
- Preserve domain terminology and identifiers from the source.
- Do not change existing IDs or silently merge separate stories.

## Output status
Finish with:
- Status: Ready for review / Incomplete — pending decisions
- Confirmed facts used
- Assumptions (if any)
- Pending questions
- Sources consulted

Do not mark a story approved. Approval is a human decision.
