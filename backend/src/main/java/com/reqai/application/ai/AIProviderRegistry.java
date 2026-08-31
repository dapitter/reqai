package com.reqai.application.ai;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AIProviderRegistry {
    private final Map<String, AIProvider> providers;

    public AIProviderRegistry(List<AIProvider> providers) {
        this.providers = providers.stream().collect(Collectors.toUnmodifiableMap(AIProvider::providerId, Function.identity()));
    }

    public AIProvider get(String providerId) {
        AIProvider provider = providers.get(providerId);
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported AI provider: " + providerId);
        }
        return provider;
    }
}
