package com.reqai.application.quality;

import java.util.List;

public record QualityAnalysisResult(
        int score,
        List<QualityFinding> findings,
        List<String> clarificationQuestions,
        List<String> evaluatedRules
) {
    public QualityAnalysisResult {
        findings = findings == null ? List.of() : List.copyOf(findings);
        clarificationQuestions = clarificationQuestions == null ? List.of() : List.copyOf(clarificationQuestions);
        evaluatedRules = evaluatedRules == null ? List.of() : List.copyOf(evaluatedRules);
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Quality score must be between 0 and 100");
        }
    }
}
