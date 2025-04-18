package com.example.kmdb.controllers;

import com.example.kmdb.dtos.GenreRequestDTO;
import com.example.kmdb.dtos.GenreResponseDTO;
import com.example.kmdb.services.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;


    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /** create a new entity
     * POST /api/{entity}
     */
    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreRequestDTO request) {
        GenreResponseDTO dto = genreService.createGenre(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // /api/genres
                .path("/{id}")           // append /{id}
                .buildAndExpand(dto.getId()) // replace {id} with the generated ID
                .toUri();

        return ResponseEntity
                .created(location) // sets 201 and location header
                .body(dto);
    }

    /** retrieve all entities
     * GET /api/{entity}:
     * returns JSON - [{"id":1,"name":"Action"}, {"id":2,"name":"Comedy"}])
     */
    @GetMapping
    public List<GenreResponseDTO> getGenres() {
        return genreService.getAllGenres();
    }

    /**
     * GET /api/{entity}/{id}
     * retrieve a specific entity by ID
     */
    @GetMapping("/{id}")
    public GenreResponseDTO getGenreId(@PathVariable Long id) {
       return genreService.getGenreById(id);
    }

    /**
     * PATCH /api/{entity}/{id}
     * modify path variable id
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyGenre(@PathVariable Long id, @Valid @RequestBody GenreRequestDTO request) {
        genreService.modifyGenresName(id, request.getName());

    }
    /**
     * DELETE /api/{entity}/{id}: Delete an entity
     * modify path variable id
     */
    @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public void deleteGenre(@PathVariable Long id) {
        genreService.removeGenre(id);

    }

}
