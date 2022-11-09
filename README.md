# Servey Microservice

Simple microservice with the following features:
* Create and edit survey
* Post answers for given survey
* Get statistics on survey participations

### Local startup

This microservice is based on Spring Boot and is running with embedded Redis instance, 
so no preparation needed. Either start from IntelliJ or build jar using maven and then run executable jar.

### Embedded redis
Embedded redis is configured to run on 9999 port by default (can be changed in application.yaml). Embedded redis is dependant on "local" spring profile (so can be easily disabled for real non-local environments with external Redis). 

### HTTP collection

You can find http collection under ./http folder to try out API. It also serves as a documentation of existing endpoints.

### Tests
There are unit and integration tests, both run by default when building with maven (test and verify phase respectively). You can also run them on demand from IntelliJ.
Tests are written in groovy using Spock and RestAssured.
