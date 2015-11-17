# maven3-unit_integration_systemtest-setup

Example Projekt with a possible Setup for Unit / Integration / System Tests

## Unit-Tests

- Only one Class / Business function ist tested
- No Database
- No Container
- Smallest possible unit to test
- Dependencies to other Classes are Mocked. (Mockito)

## Integration-Tests

- Multiple Classes / Business functions are orchestrated and tested together
- In-Memory Database per TestCase
- EE Container. Started once for all Integration Tests
- Arquillian Remote Managed Wildfly
- Datasource Deployment per TestCase inside arquillian war (*-ds.xml)
- DB Tables are generated from JPA DDL (hibernate.hbm2ddl.auto=create-drop)
- Arquillian Deployments contains only the classes neede for the testcase itself
- Testdata loaded manually inside @Test or @Before method... (test data builders...)

## System-Tests

- Full Wildfly deployment of the final WAR/EAR File.
- Container configuration via *.cli command line commands
- Read Database used because we need (hibernate.hbm2ddl.auto=validate)
- Database setup before deployment with maven-flyway-plugin
- Testdata loaded with sql-maven-plugin

