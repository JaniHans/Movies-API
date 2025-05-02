package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorRequestDto;
import com.example.kmdb.dtos.ActorResponseDto;
import com.example.kmdb.dtos.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.kmdb.dtos.MovieResponseDto;
import com.example.kmdb.dtos.PageRequestDto;
import com.example.kmdb.entities.ActorEntity;

import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.MovieRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    private static final String ACTOR_NOT_FOUND = "Actor not found by id: ";

    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;

    }
    private ActorResponseDto toDto(ActorEntity entity) {
        return new ActorResponseDto(entity.getId(), entity.getName(), entity.getBirthDate());
    }
    private Page<ActorResponseDto> toPage(Page<ActorEntity> entity) {

    }
    private MovieResponseDto movieToDto(MovieEntity entity) {
        return new MovieResponseDto(entity.getId(),
                entity.getTitle(),
                entity.getReleaseYear(),
                entity.getDuration());
    }

    /**
     * takes in entity and DTO movieids to find the movies
     * and sets entity correctly with the movies
     * @param entity
     * @param dto
     */
    private void getAndSetMoviesToActor(ActorEntity entity, ActorRequestDto dto) {
        List<MovieEntity> movies = movieRepository.findAllById(dto.getMovieIds());
        Set<MovieEntity> moviesEntities = new HashSet<>(movies);
        entity.setMovies(moviesEntities);
    }

    /**
     *POST /api/actors: Create a new actor entity
     */
    public ActorResponseDto createActor(ActorRequestDto dto) {
        ActorEntity actor = new ActorEntity();
        actor.setName(dto.getName());
        actor.setBirthDate(dto.getBirthDate());
        // helper function
        getAndSetMoviesToActor(actor, dto);
        actorRepository.save(actor);

        return new ActorResponseDto(dto.getId(), dto.getName(), dto.getBirthDate());

    }

    /*Implement methods to retrieve all actors,
    *
    */
    public Set<ActorResponseDto> getActors() {
        return actorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    /**
     * get a specific actor by ID
     */
    public ActorResponseDto getActorById(Long id) {
       return toDto(actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + id)));

    }

    /**  fetch all movies an actor has appeared in.
     * GET /api/movies?actor={Actor.id}
     */
    public Set<MovieResponseDto> getMoviesWhereActorAppeared(Long actorId) {
        actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + actorId));

        return movieRepository.findByActorsIds(actorId)
                .stream()
                .map(this::movieToDto)
                .collect(Collectors.toSet());

    }


//    Lastly, ensure you can modify an existing actor's details
    /**
     * PATCH /api/{entity}/{id}: Partially update an existing entit
     * Modify actor's name, date of birth, and associated movies.
     * Saves the changes to the actor repository.
     */
    public void modifyActor(ActorRequestDto dto) {
     ActorEntity actor = actorRepository
             .findById(dto.getId())
             .orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + dto.getId()));

     actor.setName(dto.getName());
     actor.setBirthDate(dto.getBirthDate());

     getAndSetMoviesToActor(actor, dto);

    }

    /**
     * DELETE /api/actors/1
     * DELETE /api/actors/1?force=true
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
            Set<MovieEntity> temporaryMovies = new HashSet<>(actor.getMovies());

            for (MovieEntity movie : temporaryMovies) {
                movie.getActors().remove(actor);
            }
            actorRepository.deleteById(actorId);
        }

    /**
     * GET /api/actors?name={name}: Retrieve actors filtered by name
     */
    public Set<ActorResponseDto> filterActorsByName(String name) {

        return actorRepository.findActorEntitiesByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    /**
     * Implements pagination for GET requests that return multiple entities.
     * GET /api/actors?page=0&size=10
     * returns a page object containing the result and basic pagination information
     * @param dto - page request data transfer object , page and size through getters
     */
    public Page<ActorResponseDto> getEntitiesByPageAndFilterBySize(PageRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNumber(), dto.getPageSize());
        Page<ActorEntity> page = actorRepository.findAll(pageable);


    }
}


