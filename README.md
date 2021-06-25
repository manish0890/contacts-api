# contacts-api

Contacts App Backend (RESTful service)

https://manish0890.github.io/contacts-service/   

### System Requirements
- Unix based system
- Java 11
- Docker

### Scripts/Commands to run application

Pull project dependencies
```bash
./mvnw clean install -DskipTests=true
```

Run Unit Tests
```bash
./mvnw clean test
```

Run Integration Tests
```bash
./mvnw clean -P test-it test
```

Start Application
```bash
./mvnw clean spring-boot:run
```

Once the application is running the REST documentation and tester is available via Swagger at
[http://localhost:8080/api/v1/contacts/swagger-ui.html](http://localhost:8090/api/v1/contacts/swagger-ui.html)
