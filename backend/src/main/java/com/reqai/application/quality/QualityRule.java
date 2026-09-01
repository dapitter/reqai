package com.reqai.application.quality;

public record QualityRule(
        String code,
        String category,
        String name,
        String description,
        RuleType type,
        Severity defaultSeverity,
        boolean enabled
) {
    public enum RuleType { DETERMINISTIC, AI_ASSISTED }
    public enum Severity { INFO, LOW, MEDIUM, HIGH, CRITICAL }
}
