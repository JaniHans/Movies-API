package com.example.kmdb.controllers;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.MovieDto;


import com.example.kmdb.dtos.create.CreateActorDto;

import com.example.kmdb.mapper.ActorMapper;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.services.ActorService;
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


import java.util.List;

@RestController
@RequestMapping("/api/actor")
@RequiredArgsConstructor // fields that have final create constructor
public class ActorController {

    private final ActorService actorService;
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;
    private final GenreService genreService;

    /** create a new entity
     * POST/api/actor
     */
    @PostMapping
    public ResponseEntity<ActorDto> createActor(@Valid @RequestBody CreateActorDto createActorDto) {

        return ResponseEntity.ok(actorService.createActor(createActorDto));
}
    /** retrieve all actors
     * GET/api/{entity}:
     *
     */
    @GetMapping
    public ResponseEntity<Page<ActorDto>> getAllActors(
            @PageableDefault(page = 0, size = 10)  Pageable pageable) {
        Page<ActorDto> actors = actorService.getActors(pageable);

        return ResponseEntity.ok(actors);
    }
    /**
     * retrieving a specific actor by ID
     * @param - genre id
     * @return - returns genre by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable Long id) {
        ActorDto actor = actorService.getActorById(id);
        return ResponseEntity.ok(actor);
    }
    /**
     * GET /api/movies?actor={Actor.id}: Retrieve movies that the actor with the given id has starred in
     */
    @GetMapping(params = "actor")
    public ResponseEntity<Page<MovieDto>> filterMoviesActorStarred(@RequestParam("actor") Long actorId, Pageable pageable) {
        Page<MovieDto> movies =  actorService.getMoviesWhereActorAppeared(actorId, pageable);

        return ResponseEntity.ok(movies);
    }
    /**
     * PATCH /api/{entity}/{id}
     * modify path variable id
     */
    @PatchMapping("/{id}")
    public void modifyActor(@PathVariable Long id, @RequestBody CreateActorDto actorDto) {

        actorService.modifyActor(id, actorDto);
    }

    /**
     * DELETE /api/{entity}/{id}: Delete an actor
     * modify path variable id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public ResponseEntity<Void> deleteActor(@PathVariable Long id,
                            @RequestParam(defaultValue = "false") boolean force) {

        actorService.removeActor(id, force);

        return ResponseEntity.noContent().build();

    }

    /**
     * GET /api/actors?name={name}: Retrieve actors filtered by name
     */
    @GetMapping("/api/actors")
    public List<ActorDto> filterActorsByName(@RequestParam String name) {
        return actorService.filterActorsByName(name);
    }
}
