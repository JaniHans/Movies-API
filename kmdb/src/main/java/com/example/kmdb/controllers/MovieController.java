package com.example.kmdb.controllers;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateMovieDto;
import com.example.kmdb.services.ActorService;
import com.example.kmdb.services.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor // fields that have final create constructor
public class MovieController {

    private final MovieService movieService;
    private final ActorService actorService;

    /** create new movie
     * POST /api/{movies}
     * @param request - movie request details to create new movie
     * @return movieDto of ResponseEntity for HTTP status
     */
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody CreateMovieDto request) {

        MovieDto created = movieService.createMovie(request);
        URI location = URI.create("/api/movies/" + created.getId());
    return ResponseEntity.created(location).body(created);

    }
    /**
     * @param actorId - if actor is present filter by actor
     * GET /api/movies?actor={id}
     * if not retrieve all movies
     * GET /api/movies, default pagination
     * @param page - for pagination page size
     * @param size - for pagination item size
     */
    @GetMapping()
    public ResponseEntity<Page<MovieDto>> getAllMovies(
            @RequestParam(value = "actor", required = false) Long actorId,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size) {

        Page<MovieDto> movies;
        Pageable pageable;
            // create regular pageable with page and size
        pageable = PageRequest.of(page, size);
        if (actorId != null) {
           movies = actorService.getMoviesWhereActorAppeared(actorId, pageable);
        } else {
           movies = movieService.getAllMovies(pageable);
        }
        return ResponseEntity.ok(movies);
    }

    /** retrieve all entities using pagination and search if present
     * GET /api/movies
     * GET /api/movies/search?title=matrix
     * @param title - for search criteria default empty string
     * @param page - for pagination page size
     * @param size - for pagination item size
     */
    @GetMapping("/search")
    public ResponseEntity<Page<MovieDto>> getMoviesWithSearchAndPagination(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable;
        if (title.isEmpty()) {
            // create regular pageable with page and size
            pageable = PageRequest.of(page, size);
        } else {
            pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        }
        Page<MovieDto> movies = movieService.getAllMoviesSpecial(title, pageable);
        return ResponseEntity.ok(movies);
    }
    /**
     * GET /api/{movies}/{id}
     * retrieve a specific movie by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieId(@PathVariable Long id) {
        return movieService.getMovieById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * PATCH /api/movies/{movieId}: Partially update an existing entity
     * @param id - path variable in the http request movie id
     * @param request - movie details comes in as request body
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<MovieDto> modifyMovie(@PathVariable Long id, @Valid @RequestBody CreateMovieDto request) {

        MovieDto movie = movieService.modifyMovie(id, request);
        return ResponseEntity.ok(movie);
    }
    /**
     * DELETE /api/{movies}/{id}: Delete movie
     * DELETE /api/movies/1?force=true
     * modify path variable id
     * @param id - path variable in the http request movie id
     * @param force - force delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public void deleteMovie(@PathVariable Long id,
                            @RequestParam(defaultValue = "false") boolean force) {
        movieService.removeMovie(id, force);

    }
    /**
     * GET /api/movies?genre={genreId}:
     * Retrieve movies filtered by genre
     * RequestParam - Binds from the URL query string
     */
    @GetMapping(params = "genre")
    public ResponseEntity<Page<MovieDto>> getMoviesByGenre(@RequestParam("genre") Long genreId,
                                                           @PageableDefault(page = 0, size = 10)  Pageable pageable) {
        Page<MovieDto> movies = movieService.getMoviesByGenre(genreId, pageable);
        return ResponseEntity.ok(movies);
    }
    /**
     * GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
     * Retrieve movies filtered by genre
     * RequestParam - Binds from the URL query string
     */
    @GetMapping(params = "year")
    public ResponseEntity<Page<MovieDto>> filterByYear(
            @RequestParam("year") int releaseYear,
            @PageableDefault(page = 0, size = 10)  Pageable pageable) {

        Page<MovieDto> moviesByYear = movieService.filterByYear(releaseYear, pageable);
        return ResponseEntity.ok(moviesByYear);
    }
    /**
     * get all actors in a specific movie
     * GET /api/movies/{movieId}/actors
     * returns all actors that belong to a specific movie
     */
    @GetMapping("/{id}/actors") // This is a nested resource.
    public ResponseEntity<Page<ActorDto>> getActorsInMovie(
            @PathVariable Long id,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<ActorDto> actors = movieService.getAllActorsInMovie(id, pageable);
        return ResponseEntity.ok(actors);
    }
}
