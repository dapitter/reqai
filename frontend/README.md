# ReqAI Frontend

Next.js + TypeScript frontend for the ReqAI platform.

## Product experience

The frontend is the visible product layer and will provide:

- authentication
- project dashboard
- project workspace
- requirement editor
- AI analysis workspace
- quality findings
- generated User Story / Acceptance Criteria / Business Rules
- AI provider and BYOK settings
- RAG knowledge base
- analysis history

## Initial information architecture

```text
App
├── Dashboard
├── Projects
│   └── Project Workspace
│       ├── Requirements
│       ├── Knowledge Base
│       └── Analyses
├── AI Providers
│   └── BYOK Settings
└── Account
```

## Design direction

The UI should feel like a modern B2B AI workspace: clear hierarchy, dense but readable information, strong status indicators, editable AI output, and visible provenance for generated content.
