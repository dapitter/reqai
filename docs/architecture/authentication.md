# ReqAI — Authentication & Authorization

## Access flow

Landing Page → Sign Up / Login → Organization → Dashboard

## Authentication

The production architecture will use short-lived access tokens with refresh-token rotation or a managed OIDC provider. Passwords are never stored directly; password hashing is delegated to a proven authentication component.

## Authorization

Every protected request resolves:

1. authenticated user
2. active organization
3. role
4. resource ownership

Roles:

- OWNER
- ADMIN
- ANALYST
- VIEWER

## Tenant isolation

All project-scoped data is associated with an organization. Backend authorization must enforce organization boundaries; the frontend is never trusted to provide isolation.

## BYOK security

Provider credentials are organization/user scoped and resolved only by the backend. Secrets are never returned to the browser or logged.

## MVP UX

The frontend may use mocked authentication while backend authentication is being implemented, but all screens and API contracts must preserve the production access model.
