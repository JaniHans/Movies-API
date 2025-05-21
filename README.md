# 🎬 Movies API

A RESTful API built with Spring Boot and JPA to manage a movie database for a local film society. This API enables management of movies, genres, and actors with full CRUD operations, search capabilities, and pagination.

---

## 📖 Project Overview

The API digitizes the society’s movie collection previously managed via spreadsheets. It supports:

- Full CRUD for movies, genres, and actors
- Filtering movies by genre, year, or actor
- Searching movies by title (case-insensitive, partial match)
- Pagination support for large result sets
- Input validation and custom error handling
- Safe deletion with optional force-delete mechanism

---

## 🏗️ Technologies Used

- Java 17 or Higher
- Spring Boot 3
- Spring Data JPA
- SQLite (via JDBC)
- GSON (for JSON handling)
- Postman (for testing)
- Lombok
- Logger
- Mapstruct
- Maven
- Jakarta, Hibernate validator

---

## ⚙️ Setup and Installation

1. **Clone the repository**  
   `git clone https://gitea.kood.tech/JaniHans/kmdb`

2. **Navigate to the project**  
   `cd kmdb/src/main/java/com/example/kmdb/KmdbApplication.java`

3. **Configure database**  
   In `application.properties`:
   ```properties
   spring.application.name=kmdb

   spring.datasource.url=jdbc:sqlite:movies.db
   spring.datasource.driver-class-name=org.sqlite.JDBC
   spring.jpa.database-platform=org.hibernate.dialect.SQLiteDialect
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=false
   
4. ** Add the following dependencies (copy-paste)**
   In `pom.xml`:
    ```dependencies

   	<dependencies>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId> <!--  2025 README -->
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId> <!--  2025 README -->

		</dependency>
		<dependency>
			<groupId>org.xerial</groupId>
			<artifactId>sqlite-jdbc</artifactId> <!--  2025 README -->
			<version>3.41.2.2</version>
		</dependency>
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-community-dialects</artifactId>
			<version>6.4.4.Final</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId> <!-- Latest stable version as of 2025 README -->
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.38</version> <!-- Latest stable version as of 2025 README -->
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct</artifactId> <!-- Latest stable version as of 2025 README -->
			<version>1.6.0.Beta1</version>
		</dependency>
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct-processor</artifactId>
			<version>1.6.3</version>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>  <!-- Latest stable version as of 2025 README -->
		</dependency>
		<dependency>
			<groupId>jakarta.validation</groupId>  <!-- not added to readme -->
			<artifactId>jakarta.validation-api</artifactId>
			<version>3.0.2</version>
		</dependency>
		<dependency>
			<groupId>org.hibernate.validator</groupId> <!-- not added to readme -->
			<artifactId>hibernate-validator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

🚀 Usage Guide
Base URL
http://localhost:8080/api

Endpoints Overview
Genres
POST /genres

GET /genres

GET /genres/{id}

PATCH /genres/{id}

DELETE /genres/{id}?force={true|false}

GET /genres/{id}/movies

Movies
POST /movies

GET /movies

GET /movies/{id}

PATCH /movies/{id}

DELETE /movies/{id}?force={true|false}

GET /movies?genre={id}

GET /movies?year={YYYY}

GET /movies?actor={actorId}

GET /movies/{id}/actors

GET /movies/search?title={query} ← partial, case-insensitive

GET /movies?page=0&size=10 ← pagination

Actors
POST /actors

GET /actors

GET /actors/{id}

PATCH /actors/{id}

DELETE /actors/{id}?force={true|false}

GET /actors?name={name}

📑 Sample Data
Populated with:

Genres: 5+

Movies: 20+ spanning multiple genres and decades

Actors: 15+ with varying associations

🛡️ Error Handling
Validation: Bean Validation annotations (@NotNull, @Size, etc.)

Custom Exception: ResourceNotFoundException

Global Exception Handling: Via @ControllerAdvice

Status Codes: Proper use of 200, 201, 400, 404, 204, etc.

🧪 Testing
All endpoints tested using Postman:

CRUD for all entities

Filtering and search endpoints

Validation and error scenarios

Force deletion logic
