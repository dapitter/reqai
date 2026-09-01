# ReqAI — AI Gateway

## Goal

Provide one application-facing interface for requirement analysis while isolating provider-specific APIs and credentials.

## Flow

```text
Requirement
   ↓
AnalysisUseCase
   ↓
AI Gateway
   ├── Task Router
   ├── Credential Resolver
   ├── Provider Registry
   ├── Prompt Registry
   └── Usage Meter
          ↓
 Provider Adapter
          ↓
 Model API
```

## Supported provider candidates

- DeepSeek
- Qwen
- Zhipu GLM
- Baidu ERNIE
- OpenAI
- Google Gemini
- Anthropic
- OpenAI-compatible/local endpoints

## Task types

- USER_STORY_GENERATION
- ACCEPTANCE_CRITERIA_GENERATION
- BUSINESS_RULE_EXTRACTION
- REQUIREMENT_QUALITY_ANALYSIS
- CLARIFICATION_QUESTIONS
- RAG_SYNTHESIS
- EMBEDDING

## Internal response

The gateway returns a provider-neutral result containing provider, model, normalized content, token usage, latency, and request correlation ID.

Business use cases must never instantiate provider SDK clients directly.

## BYOK boundary

Credentials are resolved server-side. The browser sends only a credential/provider reference and task configuration; secret material never crosses the browser boundary.

Production credentials must be encrypted at rest and never logged.

## Fallback policy

Fallbacks are explicit per task. The system must not silently change provider when privacy, cost, residency, or contractual constraints prohibit it.
