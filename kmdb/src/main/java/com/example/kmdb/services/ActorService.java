package com.example.kmdb.services;

import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;


    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;

    }

    /**
     *POST /api/actors: Create a new actor entity
     */
    public ActorEntity createActor(String name, LocalDate birthDate) {
        ActorEntity actor = new ActorEntity();
        actor.setName(name);
        actor.setBirthDate(birthDate);
        return actorRepository.save(actor);
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
    public ActorEntity getActorById(Integer id) {
        return actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Actor not found by id: " + id));
    }

    /**  fetch all movies an actor has appeared in.
     * GET /api/movies?actor={Actor.id}
     */
    public List<MovieEntity> getMoviesWhereActorAppeared(Integer actorId) {
        ActorEntity actor = getActorById(actorId);
        return movieRepository.findByActors(actor);
    }


//    Lastly, ensure you can modify an existing actor's details
    /**
     * Modify actor's name, date of birth, and associated movies.
     * Saves the changes to the actor repository.
     */
    public void modifyActor(Integer id, String newName, LocalDate birthDate, List<Integer> movieIds) {
     ActorEntity actor = getActorById(id);
     actor.setName(newName);
     actor.setBirthDate(birthDate);
     actor.setMovies(movieRepository.findAllById(movieIds));

     actorRepository.save(actor);

    }

    /**
     * and remove an actor from the database.
     */
    public void removeActor(Integer actorId) {
        if (!actorRepository.existsById(actorId)) {
            throw new ResourceNotFoundException("Actor with id: " + actorId + " not found");
        }
        actorRepository.deleteById(actorId);
    }
//

}
