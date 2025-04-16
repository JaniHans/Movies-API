### Service layer

## Pagination is implemented for GET requests that return multiple entities
Verify GET /api/movies?page=0&size=10 returns the first 10 movies


## ErrorHandler - DONE
Test GET /api/movies?page=-1&size=99999 and verify it returns an appropriate error response

## Search functionality
Confirm GET /api/movies/search?title=matrix returns movies with "matrix" (or any other similar example) in the title

## Case insensitive partial match search
Case-insensitive and partial match search for movie titles is implemented

## Extras
Student has implemented additional technologies, security enhancements and/or features beyond the core requirements

Getting Started
1. Clone the Repository
   git clone https://gitea.kood.tech/JaniHans/kmdb
   cd kmdb/src/main/java/com/example/kmdb
2. Requirements
   Java 17+ 
   Maven 
SQLite installed

3. Configuration
   In src/main/resources/application.properties, ensure:
   spring.application.name=kmdb

# SQLite JDBC connection
spring.datasource.url=jdbc:sqlite:kmdb.db
spring.datasource.driver-class-name=org.sqlite.JDBC

# Hibernate dialect from your dependency
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
4. Dependencies
   Ensure pom.xml includes:

<dependency>
<groupId>org.xerial</groupId>
<artifactId>sqlite-jdbc</artifactId>
<version>3.36.0.3</version>
</dependency>

5. Run the App
   ./mvnw spring-boot:run
   App will be available at: http://localhost:8080

🧪 Testing with Postman
Install Postman and import the sample collection if provided.

Test the endpoints:

Create: POST /api/movies

Read: GET /api/movies, /api/genres, /api/actors

Update: PATCH /api/movies/{id}

Delete: DELETE /api/movies/{id}?force=true

Filter/Search:

GET /api/movies?genre=1

GET /api/movies?year=2005

GET /api/movies?actor=3

GET /api/movies/search?title=matrix

Pagination: GET /api/movies?page=0&size=10

Check responses and HTTP status codes for each operation.

📦 Sample Data
Pre-load sample data to test relationships and queries:

5+ genres

20+ movies spanning 2 decades

15+ actors, many associated with multiple movies

⚙️ Extra Tips
Use @Valid for input validation in controllers

Use @ControllerAdvice for centralized exception handling

Prefer PATCH over PUT for partial updates

Avoid deleting entities with existing relationships unless force=true

📁 Project Structure
pgsql
Copy
Edit
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
└── resources