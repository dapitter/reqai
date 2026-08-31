# ReqAI — AI Model Benchmark

The ReqAI benchmark will compare providers using the same requirement-engineering dataset.

## Initial provider matrix

| Provider | Models | Priority |
|---|---|---|
| OpenAI | configurable | High |
| DeepSeek | configurable | High |
| Qwen | configurable | High |
| Zhipu GLM | configurable | High |
| Baidu ERNIE | configurable | Medium |
| Google Gemini | configurable | High |
| Anthropic | configurable | High |
| Local/Ollama | configurable | Medium |

## Evaluation tasks

1. User Story generation
2. Acceptance Criteria generation
3. Business Rule extraction
4. Ambiguity detection
5. Contradiction detection
6. Missing-information detection
7. Structured JSON output
8. RAG answer synthesis

## Metrics

- Correctness
- Completeness
- Consistency
- Structured-output validity
- Latency
- Token consumption
- Estimated cost
- Portuguese quality
- English quality
- Context handling
- Tool/function calling where applicable

## Benchmark rule

All providers must receive equivalent prompts, equivalent context, and the same evaluation dataset whenever technically possible.

Results will be recorded rather than selecting a provider based only on reputation or model popularity.
