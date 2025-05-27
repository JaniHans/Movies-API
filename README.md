# 🎬 Movies API

A RESTful API built with Spring Boot and JPA to manage a movie database for a local film society. This API enables management of movies, genres, and actors with full CRUD operations, search capabilities, and pagination.

---

## 📖 Project Overview

- Full CRUD for movies, genres, and actors
- Filtering movies by genre, year, or actor
- Searching movies by title (case-insensitive, partial match)
- Pagination support for large result sets
- Input validation and custom error handling
- Safe deletion with optional force-delete mechanism
- Data Transfer Object (DTO) - security and design enhancement (more at the end of the file)

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
   
4. ** Add the following dependencies (copy-paste if for some reason git clone does not include the dependencies)**
   In `pom.xml`:
    ```dependencies
<details>
   	<dependencies>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId> 
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId> 

		</dependency>
		<dependency>
			<groupId>org.xerial</groupId>
			<artifactId>sqlite-jdbc</artifactId> 
			<version>3.41.2.2</version>
		</dependency>
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-community-dialects</artifactId>
			<version>6.4.4.Final</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId> 
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.38</version> 
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct</artifactId> 
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
			<artifactId>spring-boot-starter-logging</artifactId> 
		</dependency>
		<dependency>
			<groupId>jakarta.validation</groupId>  
			<artifactId>jakarta.validation-api</artifactId>
			<version>3.0.2</version>
		</dependency>
		<dependency>
			<groupId>org.hibernate.validator</groupId> 
			<artifactId>hibernate-validator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
</details>

🚀 POSTMAN Usage Guide
Base URL
http://localhost:8080/api

Endpoints Overview
## Genre
POST /genres

### required JSON body

      "name" : "Comedy",
      "movies" : [movieIds] ---> [1 , 2 , 3] 

GET api/genres

GET api/genres/{id}

PATCH api/genres/{id}

### required JSON body

      "name" : "Comedy",
      "movies" : [movieIds] ---> [1 , 2 , 3] 

DELETE api/genres/{id}?force={true}

GET api/genres/{id}/movies

# Movies
POST api/movies

### required JSON Body

      "title" : "Matrix",
      "releaseYear" : 2003,
      "duration" : 150,
      "genres" : [genreIds] ---> [1 , 2 , 3]
      "actors" : [movieIds] ---> [1 , 2 , 3]

GET api/movies

GET api/movies/{id}

PATCH api/movies/{id}

# required JSON Body

      "title" : "Matrix",
      "releaseYear" : 2003,
      "duration" : 150,
      "genres" : [genreIds] ---> [1 , 2 , 3]
      "actors" : [movieIds] ---> [1 , 2 , 3]

DELETE api/movies/{id}?force={true}

GET api/movies?genre={id}

GET api/movies?year={YYYY}

GET api/movies?actor={actorId}

GET api/movies/{id}/actors

GET api/movies/search?title={query} ← partial, case-insensitive

GET api/movies?page=0&size=10 ← pagination

## Actors
POST api/actors

      "name": "John",
      "birthDate": "1990-04-12",
      "movies": [movieIds] --->  [1 , 2 , 3]

GET api/actors

GET api/actors/{id}

PATCH api/actors/{id}

      "name": "John",
      "birthDate": "1990-04-12",
      "movies": [movieIds] --->  [1 , 2 , 3]

DELETE /actors/{id}?force={true|false}

GET /actors?name={name}

📑 Sample Data
Populated with:

Genres: 5+

Movies: 20+ spanning multiple genres and decades

Actors: 15+ with varying associations

🛡️ Error Handling
Validation: Bean Validation annotations (@NotNull, @Size, etc.)

Custom Exception: ResourceNotFoundException, DeleteNotAllowedException, MethodArgumentNotValidException, InvalidDateFormatException

Global Exception Handling: Via @ControllerAdvice

Status Codes: Proper use of 200, 201, 400, 404, 204, etc.

🧪 Testing
All endpoints tested using Postman:

CRUD for all entities

Filtering and search endpoints

Validation and error scenarios

Force deletion logic

## Extra Security Enhancement

Any kind of client side entity data manipulation is dealt with a layer of abstraction called the Data Transfer Object design. Fundamental part of API structure but not specifically required in this project.

I added CreateDto(createMovieDto, createActorDto, createGenreDto) and ResponseDto(ActorDto, GenreDto, MovieDto) to improve both security and structure.

CreateDto used in POST and PATCH methods - their entity lists accepts only IDs of the entities (like actorId, genreId, movieId), so clients can’t send unwanted data or mess with entity fields.

ResponseDto gives clean output — just names or titles, not full entities — and includes only safe, needed info.

This helps avoid exposing internal details and keeps the API safer and easier to use.


