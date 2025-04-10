package com.example.kmdb.controllers;

import com.example.kmdb.dtos.GenreRequestDTO;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.services.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /** create a new entity
     * POST /api/{entity}
     */
    @PostMapping
    public GenreEntity createGenre(@Valid @RequestBody GenreRequestDTO request) {
       return genreService.createGenre(request.getName());
    }

    /** retrieve all entities
     * GET /api/{entity}:
     * returns JSON - [{"id":1,"name":"Action"}, {"id":2,"name":"Comedy"}])
     */
    @GetMapping
    public List<GenreEntity> getGenres() {
        return genreService.getAllGenres();
    }

    /**
     * GET /api/{entity}/{id}
     * retrieve a specific entity by ID
     */
    @GetMapping("/{id}")
    public GenreEntity getGenreId(@PathVariable Integer id) {
       return genreService.getGenreById(id);
    }

    /**
     * PATCH /api/{entity}/{id}
     * modify path variable id
     */
    @PatchMapping("/{id}")
    public void modifyGenre(@PathVariable Integer id, @RequestBody GenreRequestDTO request) {
       genreService.modifyGenresName(id, request.getName());
    }
    /**
     * DELETE /api/{entity}/{id}: Delete an entity
     * modify path variable id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public void deleteGenre(@PathVariable Integer id) {
        genreService.removeGenre(id);

    }

}
