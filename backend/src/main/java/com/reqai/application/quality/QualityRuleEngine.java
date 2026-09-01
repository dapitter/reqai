package com.reqai.application.quality;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class QualityRuleEngine {

    public QualityAnalysisResult analyze(String sourceText) {
        String text = sourceText == null ? "" : sourceText.trim();
        String normalized = text.toLowerCase(Locale.ROOT);
        List<QualityFinding> findings = new ArrayList<>();
        List<String> evaluated = DefaultQualityRules.all().stream().map(QualityRule::code).toList();

        if (text.isBlank()) {
            findings.add(finding("FORM-001", "FORM", QualityRule.Severity.CRITICAL,
                    "Requirement text is empty.", "No source text was provided.", "Provide a clear requirement description."));
        } else {
            if (!normalized.contains("eu como") || !normalized.contains("desejo")) {
                findings.add(finding("US-001", "USER_STORY", QualityRule.Severity.HIGH,
                        "User Story actor/intent structure was not identified.", text, "Identify the real functional actor and desired action."));
            }
            if ((normalized.contains("se ") || normalized.contains("quando ")) && !normalized.contains("então")) {
                findings.add(finding("AC-001", "ACCEPTANCE_CRITERIA", QualityRule.Severity.MEDIUM,
                        "Conditional behavior may be missing an explicit ENTÃO outcome.", text, "Express conditional behavior using SE/ENTÃO."));
            }
            if (normalized.contains("permiss") && !(normalized.contains("sem perm") || normalized.contains("não permitido") || normalized.contains("nao permitido"))) {
                findings.add(finding("AC-003", "ACCEPTANCE_CRITERIA", QualityRule.Severity.HIGH,
                        "Permission is mentioned but denial behavior is not explicit.", text, "Specify what happens when the actor does not have permission."));
            }
        }

        int score = Math.max(0, 100 - findings.stream().mapToInt(f -> switch (f.severity()) {
            case CRITICAL -> 35;
            case HIGH -> 20;
            case MEDIUM -> 10;
            case LOW -> 5;
            case INFO -> 1;
        }).sum());

        return new QualityAnalysisResult(score, findings, List.of(), evaluated);
    }

    private QualityFinding finding(String code, String category, QualityRule.Severity severity,
                                   String message, String evidence, String recommendation) {
        return new QualityFinding(code, category, severity, message, evidence, recommendation, false);
    }
}
