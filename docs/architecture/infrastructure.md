# ReqAI — Infrastructure Strategy

## Local development

Docker Compose will provide the reproducible development environment.

Initial services:

```text
postgres + pgvector
```

As application services become executable, Compose will include:

```text
frontend
backend
postgres
```

Agent Browser and other supporting services will be added only when their use case is implemented.

## Production

Hostinger is the initial target infrastructure for self-managed backend/container workloads.

The deployment architecture must keep application configuration externalized through environment variables or a secret-management mechanism.

## Principles

- No secrets in Git
- Immutable application images
- Health checks
- Persistent PostgreSQL storage with backup strategy
- TLS at the public edge
- Separate development and production configuration
- Logs and metrics available for diagnosis

## CI/CD direction

```text
GitHub
  ↓
GitHub Actions
  ├── build
  ├── unit tests
  ├── integration tests
  ├── static checks
  └── container build
          ↓
       Registry
          ↓
       Hostinger
```

## Deployment stages

1. Local Compose
2. CI validation
3. Container image
4. Staging environment
5. Production deployment

Hostinger-specific deployment details will be finalized after the required application containers and resource requirements are known.
