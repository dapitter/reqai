package com.reqai.application.quality;

public record QualityFinding(
        String ruleCode,
        String category,
        QualityRule.Severity severity,
        String message,
        String evidence,
        String recommendation,
        boolean resolved
) {}
