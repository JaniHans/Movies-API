package com.example.kmdb.services;

import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository, GenreRepository genreRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
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
        return actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actor not found with that id " + id));
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
     * modify actors name with the newname, DOB and associated movies
     * save to genre repository
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
            throw new RuntimeException("Actor not found");
        }
        actorRepository.deleteById(actorId);
    }
//

}
