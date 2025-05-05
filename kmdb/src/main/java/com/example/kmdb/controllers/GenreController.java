package com.example.kmdb.controllers;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.MovieDto;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor // fields that have final create constructor
public class GenreController {
    private final GenreService genreService;


    /** create a new entity
     * POST /api/{entity}
     */
    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@Valid @RequestBody GenreRequestDto request) {

    }

    /** retrieve all entities
     * GET /api/{entity}:
     * returns JSON - [{"id":1,"name":"Action"}, {"id":2,"name":"Comedy"}])
     */
    @GetMapping
    public ResponseEntity<Page<GenreDto>> getGenres(@PageableDefault(page = 0, size = 10)
                                                        Pageable pageable) {
        Page<GenreDto> genres = genreService.getAllGenres(pageable);
        return ResponseEntity.ok(genres);
    }

    /**
     * GET /api/{entity}/{id}
     * retrieve a specific entity by ID
     */
    @GetMapping("/{id}")
    public GenreDto getGenreId(@PathVariable Long id) {
       return genreService.getGenreById(id);
    }

    /**
     * PATCH /api/{entity}/{id}
     * modify path variable id
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyGenre(@PathVariable Long id, @Valid @RequestBody GenreDto request) {
        request.setId(id);
        genreService.modifyGenresName(request);

    }

    /**
     * GET /api/movies?genre={genreId}: Retrieve movies filtered by genre
     * @param genreId
     * @return
     */
    @GetMapping("/{genreId}")
    public List<MovieDto> filterMoviesByGenre(@PathVariable Long genreId) {
        return genreService.filterMoviesByGenre(genreId);
    }

    /**
     * DELETE /api/{entity}/{id}: Delete an entity
     * modify path variable id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id,
                            @RequestParam(defaultValue = "false") boolean force) {

        genreService.removeGenre(id, force);
        return ResponseEntity.noContent().build(); // ssa

    }

}
