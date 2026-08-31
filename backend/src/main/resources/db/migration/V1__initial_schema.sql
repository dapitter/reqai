CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), email VARCHAR(320) NOT NULL UNIQUE,
    name VARCHAR(160) NOT NULL, created_at TIMESTAMPTZ NOT NULL DEFAULT now(), updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE organizations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), name VARCHAR(160) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(), updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE organization_members (
    organization_id UUID NOT NULL REFERENCES organizations(id), user_id UUID NOT NULL REFERENCES users(id),
    role VARCHAR(40) NOT NULL, created_at TIMESTAMPTZ NOT NULL DEFAULT now(), PRIMARY KEY (organization_id, user_id)
);
CREATE TABLE projects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), organization_id UUID NOT NULL REFERENCES organizations(id),
    name VARCHAR(160) NOT NULL, description TEXT, domain_context TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(), updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE requirements (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), project_id UUID NOT NULL REFERENCES projects(id),
    title VARCHAR(255), source_text TEXT NOT NULL, status VARCHAR(40) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(), updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE requirement_versions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), requirement_id UUID NOT NULL REFERENCES requirements(id),
    version_number INTEGER NOT NULL, source_text TEXT NOT NULL, created_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(), UNIQUE (requirement_id, version_number)
);
CREATE TABLE user_stories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), requirement_version_id UUID NOT NULL REFERENCES requirement_versions(id),
    title VARCHAR(255) NOT NULL, actor TEXT NOT NULL, goal TEXT NOT NULL, benefit TEXT NOT NULL,
    generated_by_ai BOOLEAN NOT NULL DEFAULT false, approved_at TIMESTAMPTZ
);
CREATE TABLE acceptance_criteria (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), requirement_version_id UUID NOT NULL REFERENCES requirement_versions(id),
    sequence INTEGER NOT NULL, description TEXT NOT NULL, generated_by_ai BOOLEAN NOT NULL DEFAULT false,
    approved_at TIMESTAMPTZ, UNIQUE (requirement_version_id, sequence)
);
CREATE TABLE business_rules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), requirement_version_id UUID NOT NULL REFERENCES requirement_versions(id),
    code VARCHAR(40), description TEXT NOT NULL, status VARCHAR(40) NOT NULL DEFAULT 'PROPOSED',
    generated_by_ai BOOLEAN NOT NULL DEFAULT false, approved_at TIMESTAMPTZ
);
CREATE TABLE ai_analyses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), requirement_version_id UUID NOT NULL REFERENCES requirement_versions(id),
    provider VARCHAR(80) NOT NULL, model VARCHAR(120) NOT NULL, quality_score NUMERIC(5,2), status VARCHAR(40) NOT NULL,
    prompt_version VARCHAR(40), input_tokens INTEGER, output_tokens INTEGER, latency_ms INTEGER,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE quality_findings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), analysis_id UUID NOT NULL REFERENCES ai_analyses(id),
    category VARCHAR(60) NOT NULL, severity VARCHAR(30) NOT NULL, message TEXT NOT NULL,
    recommendation TEXT, resolved BOOLEAN NOT NULL DEFAULT false
);
CREATE TABLE clarification_questions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), requirement_version_id UUID NOT NULL REFERENCES requirement_versions(id),
    question TEXT NOT NULL, priority VARCHAR(30) NOT NULL, resolved BOOLEAN NOT NULL DEFAULT false, answer TEXT
);
CREATE TABLE documents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), project_id UUID NOT NULL REFERENCES projects(id),
    name VARCHAR(255) NOT NULL, mime_type VARCHAR(120), source_uri TEXT, created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE document_chunks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), document_id UUID NOT NULL REFERENCES documents(id) ON DELETE CASCADE,
    chunk_index INTEGER NOT NULL, content TEXT NOT NULL, embedding vector NOT NULL, created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (document_id, chunk_index)
);
CREATE INDEX idx_projects_organization ON projects(organization_id);
CREATE INDEX idx_requirements_project ON requirements(project_id);
CREATE INDEX idx_requirement_versions_requirement ON requirement_versions(requirement_id);
CREATE INDEX idx_ai_analyses_requirement_version ON ai_analyses(requirement_version_id);
CREATE INDEX idx_quality_findings_analysis ON quality_findings(analysis_id);
CREATE INDEX idx_documents_project ON documents(project_id);
CREATE INDEX idx_document_chunks_document ON document_chunks(document_id);
