package com.example.kmdb.controllers;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateGenreDto;

import com.example.kmdb.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/genres")
@RequiredArgsConstructor // fields that have final create constructor
public class GenreController {
    private final GenreService genreService;


    /** create a new entity
     * POST /api/{genres}
     */
    @PostMapping

    public ResponseEntity<GenreDto> createGenre(@Valid @RequestBody CreateGenreDto request) {

        GenreDto created = genreService.createGenre(request);

        URI location = URI.create("/api/genres/" + created.getId());

        return ResponseEntity.created(location).body(created);

    }

    /** retrieve all genres
     * GET /api/{genres}:
     * @param pageable -pageable object containing all genres
     */
    @GetMapping
    public ResponseEntity<Page<GenreDto>> getGenres(@PageableDefault(page = 0, size = 10)
                                                        Pageable pageable) {
        Page<GenreDto> genres = genreService.getAllGenres(pageable);
        return ResponseEntity.ok(genres);
    }

    /**
     * retrieve a specific genre by id
     * GET /api/{genres}/{id}
     * @param id - genre id
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreId(@PathVariable Long id) {

        GenreDto genre = genreService.getGenreById(id);

        return ResponseEntity.ok(genre);

    }

    /**
     * modify existing genre details
     * PATCH /api/{genres}/{id}
     * @param id - genre id
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<GenreDto> modifyGenre(@PathVariable Long id, @Valid @RequestBody CreateGenreDto request) {

        GenreDto genre = genreService.modifyGenre(id, request);

        return ResponseEntity.ok(genre);
    }



    /**
     * DELETE /api/{genres}/{id}: Delete an entity
     * DELETE /api/genres/1?force=true
     * @param id - genre id
     * @param force - force delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id,
                            @RequestParam(defaultValue = "false") boolean force) {

        genreService.removeGenre(id, force);
        return ResponseEntity.noContent().build(); // ssa

    }

}
