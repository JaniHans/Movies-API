package com.example.kmdb.controllers;

import com.example.kmdb.dtos.MovieRequestDTO;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.services.ActorService;
import com.example.kmdb.services.GenreService;
import com.example.kmdb.services.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final GenreService genreService;
    private final ActorService actorService;
    private final MovieService movieService;

    public MovieController(GenreService genreService, ActorService actorService, MovieService movieService) {
        this.genreService = genreService;
        this.actorService = actorService;
        this.movieService = movieService;
    }

    /** create a new entity
     * POST /api/{entity}
     */
    @PostMapping
    public MovieEntity createMovie(@RequestBody MovieRequestDTO requestDTO) {
        return movieService.createMovie(
                requestDTO.getTitle(),
                requestDTO.getReleaseYear(), 
                requestDTO.getDuration(),
                requestDTO.getGenreIds(),
                requestDTO.getActorIds()
        );
    }
    /** retrieve all entities
     * GET /api/{entity}:
     * returns JSON - [{"id":1,"name":"Action"}, {"id":2,"name":"Comedy"}])
     */
    @GetMapping
    public List<MovieEntity> getMovies() {
        return movieService.getAllMovies();
    }
    /**
     * GET /api/{entity}/{id}
     * retrieve a specific entity by ID
     */
    @GetMapping("/{id}")
    public MovieEntity getMovieId(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }
    /**
     * PATCH /api/{entity}/{id}: Partially update an existing entity
     * retrieve a specific entity by ID
     */
}
