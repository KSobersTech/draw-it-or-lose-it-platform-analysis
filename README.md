# Draw It or Lose It — From Architecture to API

[![Java CI](https://github.com/KSobersTech/draw-it-or-lose-it-platform-analysis/actions/workflows/ci.yml/badge.svg)](https://github.com/KSobersTech/draw-it-or-lose-it-platform-analysis/actions/workflows/ci.yml)

> An academic software-engineering portfolio project developed at Southern New Hampshire University from the fictional client scenario **The Gaming Room**. It combines the original platform analysis with a tested Java implementation of the core game–team–player domain.

## Recruiter Quick View

| Area | Verifiable evidence |
|---|---|
| Java and OOP | [`Entity`, `Game`, `Team`, and `Player`](src/main/java/tech/ksobers/drawit/domain) model inheritance, encapsulation, and composition |
| Backend engineering | [`GameController`](src/main/java/tech/ksobers/drawit/api/GameController.java) exposes a versioned REST API |
| Business rules | [`GameService`](src/main/java/tech/ksobers/drawit/domain/GameService.java) enforces scoped, case-insensitive name uniqueness |
| API quality | Bean validation and problem responses cover invalid, missing, and conflicting data |
| Testing | [Unit and HTTP integration tests](src/test/java/tech/ksobers/drawit) exercise the domain and complete API slice |
| Delivery | [CI](.github/workflows/ci.yml), Maven Wrapper, [Dockerfile](Dockerfile), health endpoint, and dependency updates |
| Technical design | [Architecture overview](docs/architecture-overview.md) connects the implementation to the recommended cloud design |

## What Works Today

The repository now contains a small Spring Boot service that implements the most important rule from the original design: one service manages games, each game owns teams, and each team owns players.

- Create and list games.
- Retrieve a complete game, including nested teams and players.
- Add teams to a game and players to a team.
- Trim names and reject blank or overlong input.
- Reject duplicate names without regard to capitalization or surrounding whitespace.
- Scope duplicate team names to a game and player names to a team.
- Return consistent JSON problem details for validation, conflicts, and missing resources.
- Report runtime health through `/actuator/health`.

Data is intentionally stored in memory, so it resets when the application restarts. Authentication, a database, game rounds, image storage, and the browser interface remain future work rather than implied features.

## API

The human- and machine-readable contract is in [`docs/openapi.yaml`](docs/openapi.yaml).

| Method | Path | Purpose |
|---|---|---|
| `GET` | `/api/v1/games` | List games with nested teams and players |
| `POST` | `/api/v1/games` | Create a game |
| `GET` | `/api/v1/games/{gameId}` | Retrieve one game |
| `POST` | `/api/v1/games/{gameId}/teams` | Add a team |
| `GET` | `/api/v1/games/{gameId}/teams/{teamId}` | Retrieve one team |
| `POST` | `/api/v1/games/{gameId}/teams/{teamId}/players` | Add a player |
| `GET` | `/api/v1/games/{gameId}/teams/{teamId}/players/{playerId}` | Retrieve one player |
| `GET` | `/actuator/health` | Check service health |

Example request:

```bash
curl -i -X POST http://localhost:8080/api/v1/games \
  -H "Content-Type: application/json" \
  -d '{"name":"Quick Draw"}'
```

## Run Locally

Prerequisite: Java 21. Maven does not need to be installed because the repository includes the Maven Wrapper.

```bash
# macOS or Linux
./mvnw verify
./mvnw spring-boot:run
```

```powershell
# Windows PowerShell
.\mvnw.cmd verify
.\mvnw.cmd spring-boot:run
```

Then open `http://localhost:8080/actuator/health` or call one of the API routes above.

To build and run the container:

```bash
docker build -t draw-it-or-lose-it .
docker run --rm -p 8080:8080 draw-it-or-lose-it
```

The runtime image uses a non-root user. Its build stage runs the complete test suite before producing the application image.

## Design Decisions

- **Spring-managed service:** `GameService` is one application-scoped service instance without a manually maintained static singleton.
- **Shared entity abstraction:** `Game`, `Team`, and `Player` inherit immutable ID and normalized-name behavior from `Entity`.
- **Scoped uniqueness:** game names are globally unique in the service; team names are unique within a game; player names are unique within a team.
- **Thread-safe in-memory writes:** synchronized aggregate operations protect the prototype from conflicting concurrent updates.
- **Immutable API shapes:** response records keep transport concerns separate from the domain model.
- **Operational baseline:** continuous integration runs `verify`, the health endpoint supports runtime checks, and Dependabot tracks Maven and workflow updates.

See the [architecture overview](docs/architecture-overview.md) for diagrams, the original platform decision, and the boundary between implemented and proposed components.

## Original Course Work

The original project analyzed how an Android-only game could become a secure, scalable, cross-platform system. My work covered requirements, object-oriented design, operating-platform tradeoffs, memory, storage, networking, distributed systems, reliability, and security.

Portfolio artifacts:

- [Architecture Overview](docs/architecture-overview.md) — implementation model, deployment recommendation, platform decision, reliability, and security.
- [Complete Portfolio PDF](Draw_It_or_Lose_It_Portfolio_Keishmar_Sobers_UNIVERSAL.pdf) — preserved course analysis and reflection.

The recommended production direction remains a Linux-based Java service behind HTTPS, with responsive browser clients, relational storage for structured data, object storage for media, identity and authorization controls, observability, redundancy, backups, and failover planning.

## Scope and Integrity

The checked-in backend is a working portfolio prototype, not a production game service. The tests verify the implemented API and domain rules. The database, authentication, responsive frontend, cloud infrastructure, and other distributed components shown in the architecture are clearly labeled recommendations and have not been presented as deployed features.

The original PDF is preserved unchanged so reviewers can distinguish the academic submission from the later implementation work.

## Skills Demonstrated

Java 21 · Spring Boot · REST APIs · Object-Oriented Design · Domain Modeling · Validation · Automated Testing · Maven · GitHub Actions · Docker · OpenAPI · Systems Analysis · Linux and Cloud Architecture · Security and Reliability Planning · Technical Communication

## About Me

I am a Computer Science student at Southern New Hampshire University pursuing a Software Engineering concentration while building a portfolio across application development, systems analysis, software design, and healthcare technology.
