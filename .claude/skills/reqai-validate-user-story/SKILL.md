---
name: reqai-validate-user-story
description: Review an existing User Story for completeness, consistency, traceability, ambiguity, and testability without silently rewriting it. Use when the user asks to validate, audit, review, or improve a User Story.
---

# ReqAI — User Story Quality Validation

## Review procedure
1. Read the complete User Story and its linked sources.
2. Search the project repository for related stories, business rules, decisions, interfaces, and integration contracts.
3. Use lexical retrieval and semantic retrieval when the ReqAI hybrid RAG index is available.
4. Compare each criterion with its evidence. Flag unsupported statements.
5. Evaluate the dimensions below.
6. Report findings with severity, evidence, impact, and a concrete recommendation.
7. Propose wording changes separately. Do not overwrite the source story unless explicitly requested.

## Quality dimensions
- Clarity: actor, action, outcome, and domain terms are understandable.
- Completeness: relevant flows, fields, validations, exceptions, and dependencies are covered.
- Consistency: no conflict with approved rules, other stories, or decisions.
- Testability: acceptance criteria have observable preconditions and outcomes.
- Atomicity: criteria are focused and not overloaded.
- Traceability: material rules and behaviors point to sources.
- Feasibility: dependencies and external integrations are identified.
- Security/privacy: access, sensitive data, audit, and privacy are considered where relevant.
- Non-functional quality: applicable performance, availability, accessibility, and compatibility constraints are addressed.

## Severity
- BLOCKER: contradictory or missing information makes implementation unsafe or impossible.
- HIGH: material ambiguity, missing business rule, or untestable core behavior.
- MEDIUM: incomplete alternate flow, validation, traceability, or dependency.
- LOW: wording, organization, or minor consistency improvement.

## Output
| ID | Severity | Dimension | Finding | Evidence | Recommendation |
|---|---|---|---|---|---|

Then provide:
- Overall readiness: Ready / Ready with reservations / Not ready
- Unsupported statements
- Conflicts and missing evidence
- Prioritized questions
- Suggested edits (separate from the original)
- Sources consulted

## Guardrails
- Do not fabricate a numeric quality score unless the project's scoring rubric is available.
- Do not treat an AI-generated rule as approved.
- Do not mark a requirement ready when a BLOCKER remains unresolved.
- Do not treat absence from search results as proof that a rule does not exist; report the search scope.
