package com.reqai.application.ai;

import java.util.Map;

public interface AIProvider {
    String providerId();
    String modelId();
    AIResponse structuredOutput(AIRequest request);

    record AIRequest(String systemPrompt, String userPrompt, Map<String, Object> context) {}
    record AIResponse(String content, int inputTokens, int outputTokens, long latencyMs) {}
}
