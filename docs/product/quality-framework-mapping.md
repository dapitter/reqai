# ReqAI — Quality Framework Mapping

## Source-derived rules

The initial framework incorporates the following guidance from the supplied SEDEC MoveMT standard:

| Source guidance | ReqAI capability |
|---|---|
| Real functional actor | Actor validation |
| SE/ENTÃO criteria | Acceptance Criteria structure check |
| Permission behavior | Access-control completeness check |
| Initial active state | List-state check |
| Filters and sorting | List interaction completeness check |
| Empty states | UX completeness check |
| Record actions by permission | Authorization coverage check |
| Pagination/count | List completeness check |
| Field type/rule/validation | Form completeness check |
| Cancellation behavior | Workflow completeness check |
| Success/failure handling | Error-handling check |
| Idempotency | Data-integrity check |
| Traceability | Traceability check |
| Security | Security requirement check |
| Non-redundant business rules | Semantic redundancy check |

The source also specifies that audit requirements should not be added to List user stories and that accessibility references should use the current eMAG version rather than WCAG. These are framework-specific checks and must remain configurable by project/framework version.

## Important boundary

The framework is not intended to silently modify an analyst's requirement. It identifies gaps, contradictions and potential improvements and presents them for human review.
