# maven3-unit_integration_systemtest-setup
Example Projekt with a posible Setup for Unit / Integration and System Tests

## Definitions

- Unit-Test: Smalles possible unit to Test. No Container, No Database. Dependencies to other Units are Mocked
- Integration-Test: Customer orchestration of Modules / Units. With or Without Container, In-Memory Database. Every Test Case have its own database. DB Creation via JPA DDL
- System-Test: Same Container as in Production. Application is deployed as it is in Production. f.Ex. Full Ear file. Real Database is used. DB Creation / Migration with Wildfly (in Our example a h2 filebased database)
