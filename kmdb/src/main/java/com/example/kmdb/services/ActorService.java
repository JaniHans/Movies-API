package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorRequestDTO;
import com.example.kmdb.dtos.ActorResponseDTO;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    private static final String ACTOR_NOT_FOUND = "Actor not found by id: ";


    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;

    }

    /**
     *POST /api/actors: Create a new actor entity
     */
    public ActorResponseDTO createActor(ActorRequestDTO dto) {
        ActorEntity actor = new ActorEntity();
        actor.setName(dto.getName());
        actor.setBirthDate(dto.getBirthDate());
        actorRepository.save(actor);

        ActorResponseDTO dtoActor = new ActorResponseDTO();
        dtoActor.setName(dto.getName());
        dtoActor.setBirthDate(dto.getBirthDate());
        dtoActor.setId(dtoActor.getId());
        return dtoActor;

    }


    /**Implement methods to retrieve all actors,
    *
    */
    public List<ActorEntity> getActors() {
        return actorRepository.findAll();
    }

    /**
     * get a specific actor by ID, and filter actors by name.
     */
    public ActorEntity getActorById(Long id) {
        return actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + id));
    }

    /**  fetch all movies an actor has appeared in.
     * GET /api/movies?actor={Actor.id}
     */
    public List<MovieEntity> getMoviesWhereActorAppeared(Long actorId) {
        ActorEntity actor = getActorById(actorId);
        return movieRepository.findByActors(actor);
    }


//    Lastly, ensure you can modify an existing actor's details
    /**
     * Modify actor's name, date of birth, and associated movies.
     * Saves the changes to the actor repository.
     */
    public void modifyActor(Long id, String newName, LocalDate birthDate, Set<Long> movieIds) {
     ActorEntity actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + id));

     actor.setName(newName);
     actor.setBirthDate(birthDate);


    }

    /**
     * and remove an actor from the database.
     */
    public void removeActor(Long actorId) {
        if (!actorRepository.existsById(actorId)) {
            throw new ResourceNotFoundException("Actor with id: " + actorId + " not found");
        }
        actorRepository.deleteById(actorId);
    }
//

}
