# ReqAI — Multi-Provider AI Strategy

## Objective

ReqAI will use an AI Gateway so the product is not coupled to a single model provider.

## Providers

The architecture must support, subject to API availability, commercial terms, regional availability, and model capabilities:

### Global providers

- OpenAI
- Google Gemini
- Anthropic

### Chinese providers

- DeepSeek
- Alibaba Qwen
- Zhipu GLM
- Baidu ERNIE

### Local / self-hosted

- Ollama-compatible models
- Other OpenAI-compatible endpoints

## AI Gateway

```text
Application
    ↓
AI Gateway
    ├── Provider Registry
    ├── Model Registry
    ├── Credential Resolver
    ├── Model Router
    ├── Prompt Manager
    ├── Usage Metering
    └── Safety / Validation
             ↓
    ┌────────┼─────────┐
    ▼        ▼         ▼
 OpenAI   DeepSeek    Qwen
 Gemini   GLM         ERNIE
 Anthropic
```

## Provider abstraction

The application must depend on an internal provider abstraction rather than provider-specific SDKs in business use cases.

Conceptual contract:

```text
AIProvider
 ├── chat()
 ├── structuredOutput()
 └── embeddings()
```

Provider adapters translate the internal contract to each provider's API.

## Model routing

Routing must be configurable by task instead of hard-coded.

Examples:

```text
USER_STORY_GENERATION
REQUIREMENT_QUALITY_ANALYSIS
RAG_SYNTHESIS
CLASSIFICATION
EMBEDDING
```

Each task can define preferred models, fallback models, cost constraints, latency targets, and context requirements.

## Chinese AI emphasis

DeepSeek, Qwen, GLM, and ERNIE are first-class candidates in the architecture, not an afterthought. The implementation will evaluate them through the same internal provider contract.

The project documentation must record model evaluation criteria such as:

- Portuguese quality
- English quality
- requirements-engineering reasoning
- structured JSON reliability
- context-window suitability
- latency
- cost
- tool/function calling capability
- availability of embeddings
- data residency considerations

## BYOK

Users may configure their own provider credentials where supported.

Credentials must:

- never be committed to Git
- never be returned by API responses
- never be exposed to the browser
- be encrypted at rest in production
- be referenced through an internal credential identifier

For local development, environment variables are allowed. Production credential storage will use a secret-management mechanism.

## Important product rule

ReqAI must not silently switch providers for a user when that could affect privacy, contractual requirements, or cost. Fallback behavior must be explicit and configurable.

## Future model evaluation

A benchmark suite will compare selected providers against the same requirement-analysis dataset. Metrics will include correctness, completeness, consistency, structured-output validity, latency, and cost.
