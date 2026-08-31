# ReqAI — API Contract v1

## Projects

### POST `/api/v1/projects`

Creates a project.

Request:

```json
{
  "organizationId": "UUID",
  "name": "ReqAI",
  "description": "Requirements engineering platform",
  "domainContext": "Software"
}
```

Response: `201 Created`

### GET `/api/v1/projects?organizationId={uuid}`

Lists projects belonging to an organization.

Response: `200 OK`

```json
[
  {
    "id": "UUID",
    "organizationId": "UUID",
    "name": "ReqAI",
    "description": "Requirements engineering platform",
    "domainContext": "Software"
  }
]
```

## API conventions

- Base path: `/api/v1`
- JSON request/response bodies
- UUID identifiers
- Validation errors return HTTP `400`
- Creation returns HTTP `201`
- Authentication will be introduced before production access
- OpenAPI documentation will be exposed by the backend

## Next endpoints

- `GET /api/v1/projects/{id}`
- `PUT /api/v1/projects/{id}`
- `DELETE /api/v1/projects/{id}`
- `POST /api/v1/requirements`
- `POST /api/v1/requirements/{id}/analyses`
