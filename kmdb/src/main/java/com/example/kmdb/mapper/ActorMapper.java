package com.example.kmdb.mapper;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.create.CreateActorDto;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.InvalidDateFormatException;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ActorMapper {

    private final MovieRepository movieRepository;

    // HELPER METHODS
    /**
     * creates genreCreateDto to genreEntity
     * sets list of movie ids to movie entities within the genre
     * @param createActorDto - actor
     * @return genre entity
     */
    public ActorEntity createActorDtoToActorEntity(CreateActorDto createActorDto) {
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
    public List<String> convertMovieEntitiesToMovieTitles(List<MovieEntity> movies) {

        return movies.stream().map(MovieEntity::getTitle)
                .toList();
    }
    /**
     * convert entity to genreDto
     * converts list of movie entities to movieDTOs
     * @param actorEntity - genre
     * @return genreDTO
     */
    public ActorDto actorEntityToActorDto(ActorEntity actorEntity) {

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
}
