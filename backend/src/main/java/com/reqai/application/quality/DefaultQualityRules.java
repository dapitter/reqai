package com.reqai.application.quality;

import java.util.List;

import static com.reqai.application.quality.QualityRule.RuleType.DETERMINISTIC;
import static com.reqai.application.quality.QualityRule.Severity.HIGH;
import static com.reqai.application.quality.QualityRule.Severity.MEDIUM;

public final class DefaultQualityRules {
    private DefaultQualityRules() {}

    public static List<QualityRule> all() {
        return List.of(
                new QualityRule("US-001", "USER_STORY", "Real functional actor", "The story must identify the real actor who performs the functionality.", DETERMINISTIC, HIGH, true),
                new QualityRule("AC-001", "ACCEPTANCE_CRITERIA", "Conditional structure", "Conditional behavior should be expressed with explicit SE/ENTÃO structure.", DETERMINISTIC, MEDIUM, true),
                new QualityRule("AC-002", "ACCEPTANCE_CRITERIA", "Validation coverage", "Relevant validations should be explicitly described.", DETERMINISTIC, MEDIUM, true),
                new QualityRule("AC-003", "ACCEPTANCE_CRITERIA", "Permission coverage", "Relevant access and permission behavior should be specified.", DETERMINISTIC, HIGH, true),
                new QualityRule("AC-004", "ACCEPTANCE_CRITERIA", "Error coverage", "Relevant business and technical failure behavior should be specified.", DETERMINISTIC, MEDIUM, true),
                new QualityRule("LIST-001", "LIST", "Initial state", "List initial state should be defined when applicable.", DETERMINISTIC, LOW, true),
                new QualityRule("LIST-002", "LIST", "Filter and sorting", "Applicable filtering and sorting behavior should be defined.", DETERMINISTIC, LOW, true),
                new QualityRule("FORM-001", "FORM", "Field validation", "Fields should define type, rule and validation where applicable.", DETERMINISTIC, MEDIUM, true),
                new QualityRule("FLOW-001", "WORKFLOW", "Cancellation", "Cancellation behavior should be defined for cancellable flows.", DETERMINISTIC, LOW, true),
                new QualityRule("DATA-001", "DATA_INTEGRITY", "Idempotency", "Operations that may be retried must define idempotency when applicable.", DETERMINISTIC, MEDIUM, true),
                new QualityRule("NFR-001", "NON_FUNCTIONAL", "Security", "Relevant security requirements should be explicitly considered.", DETERMINISTIC, HIGH, true),
                new QualityRule("TRACE-001", "TRACEABILITY", "Traceability", "Requirement artifacts should remain traceable across versions and outputs.", DETERMINISTIC, MEDIUM, true)
        );
    }
}
