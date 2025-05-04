package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.PageDto;
import com.example.kmdb.dtos.create.CreateActorDto;
import com.example.kmdb.mapper.ActorMapper;
import com.example.kmdb.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.entities.ActorEntity;

import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.MovieRepository;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor// auto create constructor that has final
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ActorMapper actorMapper;
    private final MovieMapper movieMapper;

    private static final String ACTOR_NOT_FOUND = "Actor not found by id: ";

    /**
     * takes in entity and DTO movieids to find the movies
     * and sets entity correctly with the movies
     * @param entity
     * @param dto
     */
    private void getAndSetMoviesToActor(ActorEntity entity, CreateActorDto dto) {
        List<MovieEntity> movies = movieRepository.findAllById(dto.getMovieIds());
        List<MovieEntity> moviesEntities = new ArrayList<>(movies);
        entity.setMovies(moviesEntities);
    }

    /**
     * POST /api/actor: Create a new actor entity
     */
    public ActorDto createActor(CreateActorDto createDto) {
        log.info("createActor {}, {}", createDto.getName(), createDto.getBirthDate());
        ActorEntity actorEntity = actorRepository.save(actorMapper.toEntity(createDto));
        // helper function
        getAndSetMoviesToActor(actorEntity, createDto);
        actorRepository.save(actorEntity);
        return actorMapper.toActorDto(actorEntity);
    }
    /**
     *
     *Implement methods to retrieve all actors,
    */
    public List<ActorDto> getActors() {
        log.info("Retrieving all actors");
        return actorRepository.findAll()
                .stream()
                .map(actorMapper::toActorDto)
                .collect(Collectors.toList());
    }

    /**
     * get a specific actor by ID
     */
    public ActorDto getActorById(Long id) {
       return actorMapper.toActorDto(actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + id)));

    }

    /**fetch all movies an actor has appeared in.
     * GET /api/movies?actor={Actor.id}
     */
    public List<MovieDto> getMoviesWhereActorAppeared(Long actorId) {
        actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + actorId));

        return movieRepository.findByActorsIds(actorId)
                .stream()
                .map(movieMapper::toMovieDto)
                .collect(Collectors.toList());

    }


//    Lastly, ensure you can modify an existing actor's details
    /**
     * PATCH /api/{entity}/{id}: Partially update an existing entity
     * Modify actor's name, date of birth, and associated movies.
     * Saves the changes to the actor repository.
     */
    public void modifyActor(ActorDto dto) {
     ActorEntity actor = actorRepository
             .findById(dto.getId())
             .orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + dto.getId()));

     actor.setName(dto.getName());
     actor.setBirthDate(dto.getBirthDate());
     actor.setId(dto.getId());
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
        List<MovieEntity> temporaryMovies = new ArrayList<>(actor.getMovies());

            for (MovieEntity movie : temporaryMovies) {
                movie.getActors().remove(actor);
            }
            actorRepository.deleteById(actorId);
        }

    /**
     * GET /api/actors?name={name}: Retrieve actors filtered by name
     */
    public List<ActorDto> filterActorsByName(String name) {

        return actorRepository.findActorEntitiesByNameContainingIgnoreCase(name)
                .stream()
                .map(this::)
                .collect(Collectors.toList());
    }

    /**
     * Implements pagination for GET requests that return multiple entities.
     * GET /api/actors?page=0&size=10
     * returns a page object containing the result and basic pagination information
     * @param dto - page request data transfer object , page and size through getters
     */
    public Page<ActorDto> getEntitiesByPageAndFilterBySize(PageDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNumber(), dto.getPageSize());
        Page<ActorEntity> page = actorRepository.findAll(pageable);

        return null;
    }
}


