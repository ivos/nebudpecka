# PivoSpolu backend

PivoSpolu backend project.


## Tech stack

- Java 8
- Spring Boot 2.2.5
- REST: Spring Web MVC REST controllers
- ORM: JPA / Hibernate
- Connection pool: Hikari
- DB migrations: Flyway
- DB: PostgreSQL
- API docs: Swagger
- DB tests: [Light Air](http://lightair.sourceforge.net/)


## Commands

### Clean build

To build the application WAR:

    ./mvnw clean install

The WAR is then created at `target/pivospolu-backend-*.war`.

### Run the application

To run the application from sources:

    ./mvnw -Pdb,run

Then press `CTRL-C` to stop the application.

While the app is running, you can:
- Access its homepage at [http://localhost:8080/](http://localhost:8080/)
- Execute individual Integration tests

### Re-create the database

To re-create the database tables after a new migration has been introduced:

    ./mvnw -Pdb,recreate-db

### Run System / Integration tests

To execute all Integration tests:

    ./mvnw -Pdb,it
