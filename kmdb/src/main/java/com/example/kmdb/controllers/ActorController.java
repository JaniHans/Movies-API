package com.example.kmdb.controllers;

import com.example.kmdb.dtos.ActorRequestDTO;
import com.example.kmdb.dtos.GenreRequestDTO;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.services.ActorService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;


    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }
    /** create a new entity
     * POST /api/{entity}
     */
    @PostMapping
    public ResponseEntity<ActorEntity> createActor(@Valid @RequestBody ActorRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actorService.createActor(requestDTO.getName(),requestDTO.getBirthDate()));
}
    /** retrieve all entities
     * GET /api/{entity}:
     * returns JSON - [{"id":1,"name":"Action"}, {"id":2,"name":"Comedy"}])
     */
    @GetMapping
    public List<ActorEntity> getActors() {
        return actorService.getActors();
    }
    /**
     * retrieving a specific actor by ID
     * @param - genre id
     * @return - returns genre by id
     */
    @GetMapping("/{id}")
    public ActorEntity getActorById(@PathVariable Integer id) {
        return actorService.getActorById(id);
    }
    /**
     * GET /api/movies?actor={Actor.id}: Retrieve movies that the actor with the given id has starred in
     */
    @GetMapping(params = "actor")
    public List<MovieEntity> getMoviesActorStarred(@RequestParam("actor") Integer actorId) {
        return actorService.getMoviesWhereActorAppeared(actorId);
    }
    /**
     * PATCH /api/{entity}/{id}
     * modify path variable id
     */
    @PatchMapping("/{id}")
    public void modifyActor(@PathVariable Integer id, @RequestBody ActorRequestDTO request) {
        actorService.modifyActor(id, request.getName(), request.getBirthDate(), request.getMovieIds());
    }

    /**
     * DELETE /api/{entity}/{id}: Delete an actor
     * modify path variable id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //  tells Spring to return 204 if the method completes successfully.
    public void deleteActor(@PathVariable Integer id) {
        actorService.removeActor(id);

    }
}
