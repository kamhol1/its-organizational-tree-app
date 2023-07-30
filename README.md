# its-organizational-tree-app

## How to run
Make sure you have the following installed on your machine:
- JDK 17
- Node.js
- Angular-CLI
- PostgreSQL
1. Create a PostgreSQL database. In the configuration file (src\main\resources\application-dev.yml), modify the configuration to match your database settings:
```
url: jdbc:postgresql://localhost:5432/itsapp
username: postgres
password: postgres
```
2. In the project root directory, run the command `mvnw spring-boot:run`. It will install all needed dependencies and set up the application.
3. Access the application at http://localhost:8080
