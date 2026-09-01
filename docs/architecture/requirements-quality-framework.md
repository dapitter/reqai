# ReqAI — Requirements Quality Framework

## Purpose

The Requirements Quality Framework converts project-specific requirements-engineering standards into deterministic checks and AI-assisted review criteria.

The framework is based on the approved SEDEC MoveMT documentation guidance supplied for this project. It is a reusable quality layer; it does not replace the source project standards.

## Rule categories

### User Story structure

- Actor must represent the real functional actor.
- Story uses the structure: EU COMO / DESEJO / COM O OBJETIVO DE.

### Acceptance Criteria

- Use explicit SE/ENTÃO behavior when describing conditional behavior.
- Cover access, permission, navigation, validations, actions, states, errors and relevant exceptions.
- Avoid redundant Business Rules that merely repeat Acceptance Criteria.

### Access control

- Define permission behavior.
- Unauthorized menu/actions are hidden where applicable.
- Direct access without permission is denied.
- Unauthenticated direct access redirects to authentication.

### Lists

- Define initial state.
- Define filtering and sorting.
- Define empty states.
- Define record actions according to permissions.
- Define counts and pagination where applicable.

### Forms

- Define field type, rule and validation.
- Define conditional behavior using SE/ENTÃO.
- Define cancellation behavior.
- Define success and technical-failure behavior.

### Data integrity

- Define idempotency where applicable.
- Define transactional/atomic behavior.
- Define persistence and traceability requirements.

### Non-functional requirements

- Security
- Traceability
- Integrity
- Idempotency when applicable
- Layout and responsive experience
- Auditability when applicable

## Deterministic vs AI checks

Deterministic checks should be used when a rule can be verified mechanically, such as required sections, missing fields, duplicate criteria, invalid status values or malformed structure.

AI-assisted checks should be used for semantic properties, such as ambiguity, incompleteness, contradictory statements, unclear actors, missing business context and insufficient acceptance coverage.

## Quality result

Every analysis should return:

- overall score
- deterministic findings
- AI findings
- severity
- evidence
- recommendation
- clarification questions
- framework rules evaluated

## Human review

AI findings are recommendations. The analyst remains responsible for editing and approving the final requirement artifact.

## Extensibility

The framework must support multiple standards in the future. A project can select a framework version and apply it to its requirements without changing the application core.
