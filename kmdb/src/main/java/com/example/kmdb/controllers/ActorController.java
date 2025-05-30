package com.example.kmdb.controllers;
import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.create.CreateActorDto;
import com.example.kmdb.services.ActorService;
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
@RequestMapping("/api/actors")
@RequiredArgsConstructor // fields that have final create constructor
public class ActorController {

    private final ActorService actorService;
    /** create a new actor
     * POST/api/actor
     * returns response entity actorDto
     */
    @PostMapping
    public ResponseEntity<ActorDto> createActor(@Valid @RequestBody CreateActorDto createActorDto) {

        ActorDto created = actorService.createActor(createActorDto);
        URI location = URI.create("/api/actors/" + created.getId());
        return ResponseEntity.created(location).body(created);
}
    /** retrieve all actors
     * GET/api/{entity}
     * pagination default 0, size 10
     * @param pageable - takes in pageable object
     */
    @GetMapping
    public ResponseEntity<Page<ActorDto>> getAllActors(
            @PageableDefault(page = 0, size = 10)  Pageable pageable) {

        Page<ActorDto> actors = actorService.getActors(pageable);
        return ResponseEntity.ok(actors);
    }
    /**
     * retrieving a specific actor by ID
     * @param id - genre id
     * @return - returns genre by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable Long id) {

        return actorService.getActorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /** partial actor update
     * PATCH /api/{entity}/{id}
     * @param id - Path Variable for actor id
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ActorDto> modifyActor(@PathVariable Long id, @RequestBody CreateActorDto actorDto) {

        ActorDto actor = actorService.modifyActor(id, actorDto);
        return ResponseEntity.ok(actor);
    }
    /**
     * DELETE /api/{actors}/{id}: Delete an actor
     * @param id - Path Variable for actor id
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
     * @param name - request param for string name
     * @param pageable - pageable object
     */
    @GetMapping(params = "name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Page<ActorDto>> filterActorsByName(@RequestParam("name") String name,
                                             @PageableDefault(page = 0, size = 10)  Pageable pageable) {
        Page<ActorDto> actors =  actorService.filterActorsByName(name, pageable);
            return ResponseEntity.ok(actors);

    }
}
