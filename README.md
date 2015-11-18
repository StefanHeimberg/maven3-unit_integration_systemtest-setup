# maven3-unit_integration_systemtest-setup

Example Projekt with a possible Setup for Unit / Integration / System Tests

## Requirements

- **Maven 3.3** (because usage of maven property *${maven.multiModuleProjectDirectory}*)

There is also a Problem with NetBeans 8.1. NetBeans cannot resolve dependencies with classifier. **IntelliJ 14** or newer works as expected.

    <dependency>
        <groupId>com.github.stefanheimberg.example</groupId>
        <artifactId>example-web</artifactId>
        <classifier>classes</classifier>
        <scope>test</scope>
    </dependency>


## Quickstart

    # run only unit tests
    mvn clean test
    
    # run integration tests (disabled by default)
    mvn clean verify -DskipITs=false
    
    # run system tests (disabled by default)
    mvn clean verify -DskipSTs=false

## Unit-Tests

- Only one Class / Business function ist tested
- No Database
- No Container
- Smallest possible unit to test
- Dependencies to other Classes are Mocked. (Mockito)
- Tests are inside *src/test* of the artifact where the unit under test class reside

## Integration-Tests

- Multiple Classes / Business functions are orchestrated and tested together
- In-Memory Database per TestCase
- EE Container. Started once for all Integration Tests
- Arquillian Remote Managed Wildfly
- Datasource Deployment per TestCase inside arquillian war (*-ds.xml)
- DB Tables are generated from JPA DDL (hibernate.hbm2ddl.auto=create-drop)
- Arquillian Deployments contains only the classes neede for the testcase itself
- Testdata loaded manually inside @Test or @Before method... (test data builders...)
- Tests are separated from other code

## System-Tests

- Full Wildfly deployment of the final WAR/EAR File.
- Container configuration via *.cli command line commands
- Real Database used because we need (hibernate.hbm2ddl.auto=validate)
- Database setup before deployment with maven-flyway-plugin
- Testdata loaded with sql-maven-plugin
- Tests are separated from other code


## Links

Linked at StackOverflow [Maven: How can I setup maven to run unit test and integration test separately](http://stackoverflow.com/a/33774026/4429292)
