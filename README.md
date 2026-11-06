# assets-manager-backend

Spring Boot 4 / Java 21 REST API for the Assets Manager app. Stores assets and their maintenance records in PostgreSQL.

## Run

Requires a running local PostgreSQL (18) with an empty database created for the app.

```bash
./mvnw spring-boot:run      # API on http://localhost:8080
```

### Configuration

Everything is read from environment variables at startup:

| Variable | Purpose | Default |
|---|---|---|
| `DB_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/assets-manager` |
| `DB_USERNAME` | DB user | none (required) |
| `DB_PASSWORD` | DB password | none (required) |
| `CORS_ALLOWED_ORIGINS` | Allowed frontend origins, comma-separated | `http://localhost:3000,http://localhost:5173` |

On the hosting service, set these as environment variables. Locally, put the DB settings in the git-ignored `config/application.properties` (Spring Boot loads it automatically).
Tests use an in-memory H2 database, so no Postgres is needed for `./mvnw test`.

## API

| Method | Path | Description |
|---|---|---|
| GET | `/api/dashboard/assets` | Return all assets as JSON |
| GET | `/api/assets` | List assets |
| POST | `/api/assets` | Create asset (JSON) |
| GET / PUT / DELETE | `/api/assets/{id}` | Read / update / delete asset |
| PUT | `/api/assets/{id}/image` | Upload/replace image (multipart, field `file`; jpeg/png/gif/webp, max 5MB) |
| GET / DELETE | `/api/assets/{id}/image` | Download / remove the image |
| GET / POST | `/api/assets/{assetId}/maintenance` | List / add maintenance records for an asset |
| GET / PUT / DELETE | `/api/maintenance/{id}` | Read / update / delete a maintenance record |

Example:

```bash
curl -X POST localhost:8080/api/assets -H "Content-Type: application/json" \
  -d '{"name":"Dell Latitude 7440","category":"Laptop"}'
```

Health check: `/api/actuator/health`.

## Notes

- Schema is auto-managed by Hibernate (`ddl-auto=update`) for now; move to Flyway/Liquibase before production.
- No authentication yet.
