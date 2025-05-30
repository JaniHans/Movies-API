package com.example.kmdb.mapper;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.create.CreateActorDto;
import com.example.kmdb.entities.Actor;
import com.example.kmdb.exceptions.InvalidDateFormatException;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class ActorMapper {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    // HELPER METHODS
    /**
     * creates genreCreateDto to genreEntity
     * sets list of movie ids to movie entities within the genre
     * @param createActorDto - actor
     * @return genre entity
     */
    public Actor createActorDtoToActorEntity(CreateActorDto createActorDto) {
        if (createActorDto == null || createActorDto.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Name of the actor can't be null or empty");
        }

        Actor actor = new Actor();
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
     * convert entity to genreDto
     * converts list of movie entities to movieDTOs
     * @param actor - genre
     * @return genreDTO
     */
    public ActorDto actorEntityToActorDto(Actor actor) {

        if (actor == null || actor.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Entity can't be null");
        }
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actor.getId());
        actorDto.setName(actor.getName());
        actorDto.setBirthDate(actor.getBirthDate());

        actorDto.setMovies(movieMapper.convertMovieEntitiesToMovieTitles(actor.getMovies()));
        return actorDto;
    }
}
