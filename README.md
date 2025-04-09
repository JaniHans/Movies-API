### Service layer

## GenreService - DONE
## GenreController - DONE
## MovieService - Started
## MovieController - Started
## ActorService toDO
## ActorController - ToDO

### NEXT STEPS

# TEST YOUR API
All CRUD operations work
Testing the API
To ensure your API works as expected, you'll use Postman to test it:

Install Postman if you haven't already.
Create a new collection named Movie Database API.
For each entity (Genre, Movie, Actor), create requests for all CRUD operations:
POST to create new entities
GET to retrieve entities
PATCH to update entities
DELETE to remove entities
For filtered GET requests (e.g., movies by genre), test with various query parameters.
For POST and PATCH requests, set up JSON request bodies with sample data.
Send requests and examine the responses
Check status codes (e.g., 200 for successful GET, 201 for successful POST)
Verify response bodies contain expected data
Test error scenarios
Try to retrieve non-existent entities
Attempt to create entities with invalid data
Try to delete entities with existing relationships

# Error Handling
Input Validation: Use Bean Validation annotations (like @NotNull, @Size) on your entity fields. 
Apply the @Valid annotation to your controller methods to enforce these validations
Custom Exceptions: Create a ResourceNotFoundException to handle cases where requested data isn't found in the database.

Global Exception Handling: Implement a global exception handler using @ControllerAdvice. 
This should catch and handle common exceptions, 
your custom ResourceNotFoundException and validation errors.

Errors (like 404s) are returned correctly

HTTP Status Codes and Error Messages: Return appropriate HTTP status codes (200 OK, 201 Created, 204 No Content, 404 Not Found, etc.). 
Provide clear, concise error messages that explain what went wrong.


#### Bonus (Optional Extras)
# Add pagination and sorting
Implement pagination for GET requests that return multiple entities.

Add page and size parameters to your GET endpoints
Return a Page object containing the results and basic pagination information.
GET /api/movies?page=0&size=10
# Create response DTOs for cleaner output
# Basic search
Add a simple search functionality for movies by title.

Implement a case-insensitive, partial match search.
Return a list of movies that match the search criteria.
GET /api/movies/search?title=matrix
# Add integration tests using @SpringBootTest

