package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorDto;

import com.example.kmdb.dtos.create.CreateActorDto;

import com.example.kmdb.exceptions.InvalidDateFormatException;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;

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

@Service
@Slf4j
@RequiredArgsConstructor// auto create constructor that has final
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    private static final String ACTOR_NOT_FOUND = "Actor not found by id: ";

    // HELPER METHODS
    /**
     * creates genreCreateDto to genreEntity
     * sets list of movie ids to movie entities within the genre
     * @param createActorDto - actor
     * @return genre entity
     */
    private ActorEntity createActorDtoToActorEntity(CreateActorDto createActorDto) {
        if (createActorDto == null || createActorDto.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Name of the actor can't be null or empty");
        }

        ActorEntity actor = new ActorEntity();
        actor.setName(createActorDto.getName());


        try {
            LocalDate birthDate = LocalDate.parse(createActorDto.getBirthDate());
            actor.setBirthDate(birthDate);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format. Expected: yyyy-MM-dd");
        }


        if (createActorDto.getMovies().isEmpty()) {
            return actor;
        }

        actor.setMovies(movieRepository.findAllById(createActorDto.getMovies()));
        return actor;
    }

    /**
     * Returns a list of movie titles that belong to the entity
     * @param movies
     */
    private List<String> convertMovieEntitiesToMovieTitles(List<MovieEntity> movies) {

        return movies.stream().map(MovieEntity::getTitle)
                .toList();
    }


    /**
     * convert entity to genreDto
     * converts list of movie entities to movieDTOs
     * @param actorEntity - genre
     * @return genreDTO
     */
    private ActorDto actorEntityToActorDto(ActorEntity actorEntity) {

        if (actorEntity == null || actorEntity.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Entity can't be null");
        }
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actorEntity.getId());
        actorDto.setName(actorEntity.getName());
        actorDto.setBirthDate(actorEntity.getBirthDate());

        actorDto.setMovies(convertMovieEntitiesToMovieTitles(actorEntity.getMovies()));
        return actorDto;
    }

    // CRUD METHODS

    /**
     * POST /api/actor: Create a new actor entity
     */
    public ActorDto createActor(CreateActorDto createDto) {
        log.info("createActor {}, {}", createDto.getName(), createDto.getBirthDate());

        ActorEntity actor = createActorDtoToActorEntity(createDto);
        // find by movie IDS
        List<MovieEntity> movies = movieRepository.findAllById(createDto.getMovies());

        for (MovieEntity movie : movies) {
            movie.getActors().add(actor);
        }

        actor.setMovies(movies);

        actorRepository.save(actor);
        return actorEntityToActorDto(actor);

    }
    /**
     *Implement methods to retrieve all actors, pagination included
     * GET /api/actors?page=0&size=10
     * return pageable object
    */
    public Page<ActorDto> getActors(Pageable pageable) {

       log.info("Retrieving all actors");
       Page<ActorEntity> actors = actorRepository.findAll(pageable);

       return actors.map(this::actorEntityToActorDto);

    }

    /**
     * get a specific actor by ID
     */
    public ActorDto getActorById(Long id) {

        ActorEntity actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + id));

        return actorEntityToActorDto(actor);
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
       return actorEntityToActorDto(actor);
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
        return actorRepository.findActorEntitiesByNameIgnoreCase(name, pageable)
        .map(this::actorEntityToActorDto);


    }

}


