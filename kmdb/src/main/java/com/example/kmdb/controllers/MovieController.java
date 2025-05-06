package com.example.kmdb.controllers;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateMovieDto;
import com.example.kmdb.repositories.MovieRepository;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor // fields that have final create constructor
public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    /** create a new entity
     * POST /api/{entity}
     */
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody CreateMovieDto request) {
    MovieDto dto = movieService.createMovie(request);

    return ResponseEntity.ok(dto);

    }
    /** retrieve all entities
     * GET /api/{entity}:
     * returns JSON - [{"id":1,"name":"Action"}, {"id":2,"name":"Comedy"}])
     * GET /api/movies/search?title=matrix
     */
    @GetMapping
    public ResponseEntity<Page<MovieDto>> getMovies(
            @RequestParam(defaultValue = "") String searchCriteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable;
        if (searchCriteria.isEmpty()) {
            // create regular pageable with page and size
            pageable = PageRequest.of(page, size);
        } else {
            pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        }
        Page<MovieDto> movies = movieService.getAllMovies(searchCriteria, pageable);

        return ResponseEntity.ok(movies);
    }
    /**
     * GET /api/{movie}/{id}
     * retrieve a specific movie by ID
     */
    @GetMapping("/{id}")
    public MovieDto getMovieId(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }
    /**
     * PATCH /api/{entity}/{id}: Partially update an existing entity
     * retrieve a specific entity by ID
     */
    @PatchMapping("/{id}")
    public void modifyMovie(@PathVariable Long id, @RequestBody CreateMovieDto request) {

        movieService.modifyMovie(id, request);
    }
    /**
     * DELETE /api/{entity}/{id}: Delete an entity
     * modify path variable id
     *  PathVariable - Binds from the URL path
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public void deleteMovie(@PathVariable Long id,
                            @RequestParam(defaultValue = "false") boolean force) {
        movieService.removeMovie(id, force);

    }
    // Filtering endpoints
    /**
     * GET /api/movies?genre={genreId}:
     * Retrieve movies filtered by genre
     * RequestParam - Binds from the URL query string
     */
    @GetMapping(params = "genre")
    // Only accept the request if there is a query parameter called genreId present.
    public List<MovieDto> getMoviesByGenre(@RequestParam("genre") Long genreId) {
        return movieService.getMoviesByGenre(genreId);
    }
    /**
     * GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
     * Retrieve movies filtered by genre
     * RequestParam - Binds from the URL query string
     */
    @GetMapping(params = "releaseYear")
    public ResponseEntity<Page<MovieDto>> filterByYear(
            @RequestParam int releaseYear,
    @PageableDefault(page = 0, size = 10)  Pageable pageable) {
        Page<MovieDto> moviesByYear = movieService.filterByYear(releaseYear, pageable);
        return ResponseEntity.ok(moviesByYear);
    }

    /**
     * GET /api/movies?genre={genreId}: Retrieve movies filtered by genre
     * returns all actors that belong to a specific movie
     */
    @GetMapping("/{id}/actors") // This is a nested resource.
    public ResponseEntity<Page<ActorDto>> getActorsInMovie(@PathVariable Long id, @PageableDefault(page = 0, size = 10)  Pageable pageable) {

        Page<ActorDto> actors = movieService.getAllActorsInMovie(id, pageable);

        return ResponseEntity.ok(actors);
    }

}
