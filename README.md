# Draw It or Lose It — Software Design & Platform Analysis

> **Academic software-engineering portfolio project** developed at Southern New Hampshire University from the fictional client scenario **The Gaming Room**. The repository focuses on requirements analysis, object-oriented design, platform evaluation, distributed architecture, security, and technical decision-making.

## Recruiter Quick View

| Area | Evidence in this project |
|---|---|
| Object-oriented design | Java domain model using `Entity`, `Game`, `Team`, `Player`, and `GameService` |
| Design patterns | Singleton service pattern, inheritance, encapsulation, collection lookup and duplicate prevention |
| Systems analysis | Client requirements translated into technical constraints and architecture decisions |
| Platform engineering | Linux, Windows, macOS, Android, and iOS evaluated for server/client roles |
| Distributed systems | Responsive clients, Java backend services, RESTful HTTPS communication, relational data, object storage |
| Security | Authentication, authorization, TLS, least privilege, encryption, logging, monitoring, and patching |
| Reliability | Load balancing, redundancy, backups, health monitoring, and failover planning |
| Technical communication | Full software design document and concise architecture documentation |

## Portfolio Artifacts

- **[Architecture Overview](docs/architecture-overview.md)** — recruiter-friendly domain model, deployment diagram, platform decision, storage/memory analysis, reliability, and security.
- **[Complete Portfolio PDF](./Draw_It_or_Lose_It_Portfolio_Keishmar_Sobers_UNIVERSAL.pdf)** — preserved software design and platform analysis artifact.

## Project Overview

**Draw It or Lose It** is a software design and operating-platform analysis for a game application that needed to expand from an Android-only product into a secure, scalable, web-based system capable of supporting users across desktop and mobile platforms.

The project addresses two connected engineering problems:

1. Design a maintainable object model for games, teams, players, identifiers, and duplicate-name controls.
2. Recommend an operating and deployment architecture capable of supporting future cross-platform growth.

This is primarily a **software-design and systems-analysis project**, not a claim that the complete cloud environment or production game was deployed.

## My Role

I approached the scenario from the perspective of a junior software designer and systems analyst. I reviewed the client requirements, evaluated architectural constraints, analyzed operating-platform options, and developed a technical recommendation that connected business needs to implementation decisions.

My work included:

- Translating client requirements into software constraints.
- Evaluating and documenting an object-oriented Java design.
- Applying inheritance, encapsulation, and the singleton pattern.
- Analyzing Linux, Windows, macOS, Android, and iOS platform considerations.
- Recommending a Linux-based cloud backend with responsive web clients.
- Evaluating memory, storage, networking, distributed systems, scalability, reliability, and security.
- Communicating the recommendation in a client-centered technical design document.

## Object-Oriented Design

The application model centers on a `GameService` class that manages game creation, lookup, and identifier generation. `Game`, `Team`, and `Player` share common ID and name behavior through an `Entity` base class.

Important design decisions include:

- A singleton `GameService` for one controlled service instance in the prototype.
- Inheritance to reduce duplicated fields and behavior.
- Encapsulation to protect object state.
- Collection searches before object creation to prevent duplicate game and team names.
- Modular responsibilities that can later be connected to persistence, APIs, and user interfaces.

The **[Architecture Overview](docs/architecture-overview.md)** contains a GitHub-rendered Mermaid class diagram of the domain model.

## Recommended Technical Architecture

The recommended solution uses a **Linux-based cloud environment** for backend services and a **responsive web application** for client access. This separates the shared game logic from specific client devices and provides a path to support Windows, macOS, Android, iOS, and browser users without maintaining separate backends.

The proposed architecture includes:

- Java-based application services.
- RESTful communication over HTTPS.
- Relational database storage for structured application data.
- Cloud object storage for drawings, images, logs, and backups.
- Authentication and role-based authorization.
- Encryption in transit and at rest.
- Monitoring, logging, and least-privilege controls.
- Load balancing, redundancy, backups, and failover planning.

## Platform Recommendation

Linux was selected as the strongest backend option because it provides a good combination of cloud compatibility, server stability, automation, cost control, Java support, and scalability.

Windows remains a viable server alternative where Microsoft infrastructure is already central. macOS is better positioned for development and Apple-platform testing than general production hosting. Android and iOS should operate as clients connecting to the shared backend rather than host the server-side application.

## Storage, Memory, Networking, and Security

The project goes beyond selecting an operating system and examines how the application would behave in a distributed environment.

### Storage and Memory

- Relational storage for accounts, sessions, teams, scores, and game history.
- Object storage for images, drawings, logs, and backups.
- Linux virtual memory and process isolation for server stability.
- JVM heap, stack, and garbage-collection considerations for the Java service.

### Networking and Reliability

- Distributed client-server communication through secure web APIs.
- Load balancing to distribute traffic.
- Redundant components and failover planning.
- Monitoring, health checks, backup verification, and recovery procedures.

### Security

- HTTPS/TLS for all client-server communication.
- Secure password hashing and salting.
- Strong authentication and role-based authorization.
- Input validation and protected API endpoints.
- Least-privilege access controls.
- Encryption for sensitive stored data and backups.
- Audit logging, monitoring, regular patching, and vulnerability response.

## Skills Demonstrated

- Java
- Object-Oriented Programming
- UML and Domain Modeling
- Software Design Patterns
- Requirements Analysis
- Systems Analysis
- Operating Systems
- Linux Fundamentals
- Client-Server Architecture
- RESTful Architecture
- Distributed Systems
- Relational Database Concepts
- Cloud Architecture Fundamentals
- Security Engineering Fundamentals
- Reliability and Scalability Planning
- Technical Documentation

## What I Learned

This project strengthened my ability to move from a client problem to a structured technical recommendation. It reinforced that software engineering requires more than writing code: platform compatibility, data integrity, security, memory, storage, networking, reliability, scalability, and maintainability all shape the quality of the final system.

It also helped me practice explaining why a technical choice fits a business requirement rather than selecting technology simply because it is familiar.

## Scope and Integrity Note

The full architecture represents a **technical design recommendation developed for an academic systems-design project**. The Java prototype concepts and object model were used to demonstrate software-design principles, while the cloud, database, authentication, and distributed deployment sections describe recommended future architecture rather than a released production system.

The original portfolio artifact is preserved in the repository so the work can be reviewed in context.

## About Me

I am a Computer Science student at Southern New Hampshire University pursuing a Software Engineering concentration while building a portfolio across application development, systems analysis, software design, and healthcare technology.
