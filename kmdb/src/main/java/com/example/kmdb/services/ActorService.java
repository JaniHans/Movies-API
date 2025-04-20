package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorRequestDTO;
import com.example.kmdb.dtos.ActorResponseDTO;
import com.example.kmdb.dtos.GenreResponseDTO;
import com.example.kmdb.dtos.MovieResponseDTO;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private ActorResponseDTO toDto(ActorEntity entity) {
        return new ActorResponseDTO(entity.getId(), entity.getName(), entity.getBirthDate());
    }
    private MovieResponseDTO movieToDto(MovieEntity entity) {
        return new MovieResponseDTO(entity.getId(),
                entity.getTitle(),
                entity.getReleaseYear(),
                entity.getDuration());
    }

    /**
     * takes in entity and DTO movieids to find the movies and sets entity correctly with the aforementioned movies
     * @param entity
     * @param dto
     */
    private void getAndSetMoviesToActor(ActorEntity entity, ActorRequestDTO dto) {
        List<MovieEntity> movies = movieRepository.findAllById(dto.getMovieIds());
        Set<MovieEntity> moviesEntities = new HashSet<>(movies);
        entity.setMovies(moviesEntities);
    }

    /**
     *POST /api/actors: Create a new actor entity
     */
    public ActorResponseDTO createActor(ActorRequestDTO dto) {
        ActorEntity actor = new ActorEntity();
        actor.setName(dto.getName());
        actor.setBirthDate(dto.getBirthDate());
        // helper function
        getAndSetMoviesToActor(actor, dto);
        actorRepository.save(actor);

        return new ActorResponseDTO(dto.getId(), dto.getName(), dto.getBirthDate());

    }


    /**Implement methods to retrieve all actors,
    *
    */
    public Set<ActorResponseDTO> getActors() {
        return actorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    /**
     * get a specific actor by ID
     */
    public ActorResponseDTO getActorById(Long id) {
       return toDto(actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + id)));

    }

    /**  fetch all movies an actor has appeared in.
     * GET /api/movies?actor={Actor.id}
     */
    public Set<MovieResponseDTO> getMoviesWhereActorAppeared(Long actorId) {
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
    public void modifyActor(ActorRequestDTO dto) {
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
                  + actor.getMovies().size() + " assoiciated movies.");
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
    public Set<ActorResponseDTO> filterActorsByName(String name) {

        return actorRepository.findActorEntitiesByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
    }


