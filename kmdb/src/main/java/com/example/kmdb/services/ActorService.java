package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorDto;

import com.example.kmdb.dtos.create.CreateActorDto;

import com.example.kmdb.exceptions.InvalidDateFormatException;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;

import com.example.kmdb.mapper.ActorMapper;
import com.example.kmdb.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.entities.ActorEntity;

import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.MovieRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor// auto create constructor that has final
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ActorMapper actorMapper;
    private final MovieMapper movieMapper;

    private static final String ACTOR_NOT_FOUND = "Actor not found by id: ";
    private final MovieService movieService;


    /**
     * POST /api/actor: Create a new actor entity
     */
    public ActorDto createActor(CreateActorDto createDto) {
        log.info("createActor {}, {}", createDto.getName(), createDto.getBirthDate());

        ActorEntity actor = actorMapper.createActorDtoToActorEntity(createDto);
        // find by movie IDS
        List<MovieEntity> movies = movieRepository.findAllById(createDto.getMovies());

        for (MovieEntity movie : movies) {
            movie.getActors().add(actor);
        }

        actor.setMovies(movies);

        actorRepository.save(actor);
        return  actorMapper.actorEntityToActorDto(actor);

    }

    /**
     *Implement methods to retrieve all actors, pagination included
     * GET /api/actors?page=0&size=10
     * return pageable object
    */
    public Page<ActorDto> getActors(Pageable pageable) {

       log.info("Retrieving all actors");
       Page<ActorEntity> actors = actorRepository.findAll(pageable);

       return actors.map(actorMapper::actorEntityToActorDto);

    }
    /**
     * get a specific actor by ID
     */
    public ActorDto getActorById(Long id) {

        ActorEntity actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + id));

        return actorMapper.actorEntityToActorDto(actor);
    }

//    Lastly, ensure you can modify an existing actor's details
    /**
     * PATCH /api/{actor}/{id}
     * Partially update an existing entity
     * Modify actor's name, date of birth, and associated movies.
     * Saves the changes to the actor repository.
     * @param actorId - actor id
     * @param actorDto - createActorDto
     */
    public ActorDto modifyActor(Long actorId, CreateActorDto actorDto) {
     ActorEntity actor = actorRepository
             .findById(actorId)
             .orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + actorId));


        if (actorDto.getMovies().isEmpty()) {

            List<MovieEntity> movies = actor.getMovies();

            for (MovieEntity movie : movies) {
                movie.getActors().remove(actor);
            }

            actor.getMovies().clear();
        } else {
            // empty genres from movies before adding new movies, replace and delete old basically
            for (MovieEntity movies : actor.getMovies()) {
                movies.getActors().remove(actor);
            }
            actor.setMovies(movieRepository.findAllById(actorDto.getMovies()));

            for (MovieEntity moviesNew : actor.getMovies()) {
                moviesNew.getActors().add(actor);
            }
        }

     actor.setName(actorDto.getName());

        try {
            LocalDate birthDate = LocalDate.parse(actorDto.getBirthDate());
            actor.setBirthDate(birthDate);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format. Expected: yyyy-MM-dd");
        }

     actorRepository.save(actor);
       return actorMapper.actorEntityToActorDto(actor);
    }

    /**
     * Remove actor from the database
     * Force delete if actor has associated movies
     * DELETE /api/actors/1
     * DELETE /api/actors/1?force=true
     * @param actorId - actor id
     * @param force - boolean for force deletion
     * and remove an actor from the database.
     */
    public void removeActor(Long actorId, boolean force) {
      ActorEntity actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + actorId));

        // if associations exist and force is not true
      if (!actor.getMovies().isEmpty() && !force) {
          throw new DeleteNotAllowedException("Cannot delete actor "
                  + actor.getName()
                  + " because it has "
                  + actor.getMovies().size() + " associated movies.");
      }

      if (actor.getMovies().isEmpty()) {
          actorRepository.deleteById(actorId);
      }
      // force break
        if (force) {
            // reference to movie entity objects
        }
        List<MovieEntity> temporaryMovies = new ArrayList<>(actor.getMovies());

            for (MovieEntity movie : temporaryMovies) {
                movie.getActors().remove(actor);
            }
            actorRepository.deleteById(actorId);
            log.info("Actor deleted");
        }

    /**
     * GET /api/actors?name={name}: Retrieve actors filtered by name
     * @param name - actor name
     * @param pageable - pageable object
     */
    public Page<ActorDto> filterActorsByName(String name, Pageable pageable) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Actor name can't be empty");
        }

           Optional<ActorEntity> actor = actorRepository.findActorEntityByNameEqualsIgnoreCase(name);
           if (actor.isEmpty()) {
               throw new ResourceNotFoundException("Actor not found");
           }
           return actorRepository.findActorEntitiesByNameContainingIgnoreCase(name, pageable)
                   .map(actorMapper::actorEntityToActorDto);

    }
    /**
     * fetch all movies an actor has appeared in.
     * GET /api/movies?actor={Actor.id} request method
     * Pagination
     * @param actorId -actor id
     * @param pageable - pageable object
     * @return MovieDto of page type
     */
    public Page<MovieDto> getMoviesWhereActorAppeared(Long actorId, Pageable pageable) {
        actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + actorId));

        return movieRepository.findByActorsIds(actorId, pageable)
                .map(movieMapper::movieEntityToMovieDto);

    }

}
