# TaskFlow

A task tracker that consolidates tasks across all active skills (Java Core, Spring, Databases, Git, Design Patterns…) and shows progress for each skill.

## Modules

| Module          | What's Inside                                             | Status                   |
|-----------------|-----------------------------------------------------------|--------------------------|
| `taskflow-core` | Domain model, collections, algorithms, patterns—pure Java | In Progress              |
| `taskflow-api`  | Spring Boot: REST, CRUD, PostgreSQL, validation           | Starting in the 6th week |

## Stack

Java 21 · Maven · JUnit 5 · (next) Spring Boot 3 · PostgreSQL · Flyway

## Launch

```bash
mvn clean verify                                   # compile and run the tests
java -cp taskflow-core/target/classes com.taskflow.core.TaskFlowApp
```

## Workflow

1. Each task is a ticket in Jira (`TF-N`).
2. A branch from `main`: `feature/TF-N-short-name` (correction — `fix/TF-N-...`).
3. Commits with the ticket key: `TF-N: what has been done`.
4. Merge request in `main` -> verification -> merge -> ticket to Done.

## Documentation

- `docs/data-structures.md` - Selection of Data Structures and Algorithms (TF-6)
- `docs/design.md` - SOLID and Patterns (TF-8)
- `docs/nosql.md` - Notes on NoSQL (TF-10)
