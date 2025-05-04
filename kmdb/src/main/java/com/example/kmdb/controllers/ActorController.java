package com.example.kmdb.controllers;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.MovieDto;

import com.example.kmdb.dtos.create.CreateActorDto;
import com.example.kmdb.services.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/api/actors")
@RequiredArgsConstructor // fields that have final create constructor
public class ActorController {

    private final ActorService actorService;

    /** create a new entity
     * POST /api/{entity}
     */
    @PostMapping
    public ResponseEntity<ActorDto> createActor(@Valid @RequestBody CreateActorDto createActor) {

}
    /** retrieve all entities
     * GET /api/{entity}:
     * returns JSON - [{"id":1,"name":"Action"}, {"id":2,"name":"Comedy"}])
     */
    @GetMapping
    public List<ActorDto> getActors() {
        return actorService.getActors();
    }
    /**
     * retrieving a specific actor by ID
     * @param - genre id
     * @return - returns genre by id
     */
    @GetMapping("/{id}")
    public ActorDto getActorById(@PathVariable Long id) {
        return actorService.getActorById(id);
    }
    /**
     * GET /api/movies?actor={Actor.id}: Retrieve movies that the actor with the given id has starred in
     */
    @GetMapping(params = "actor")
    public List<MovieDto> getMoviesActorStarred(@RequestParam("actor") Long actorId) {
        return actorService.getMoviesWhereActorAppeared(actorId);
    }
    /**
     * PATCH /api/{entity}/{id}
     * modify path variable id
     */
    @PatchMapping("/{id}")
    public void modifyActor(@PathVariable Long id, @RequestBody ActorDto dto) {

        actorService.modifyActor(dto);
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

    @PostMapping
    public Page<ActorDto> getAllActorsUsingPagination(@RequestBody PageRequest dto) {

    }


}
