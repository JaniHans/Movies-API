package com.example.kmdb.mapper;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateMovieDto;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor

public class MovieMapper {

    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    /**
     * @param movies
     */
    public List<String> convertMovieEntitiesToMovieTitles(List<MovieEntity> movies) {

        return movies.stream().map(MovieEntity::getTitle)
                .toList();
    }

    public MovieEntity createMovieDtoToMovieEntity(CreateMovieDto createMovieDto) {

        if (createMovieDto == null || createMovieDto.getTitle().isBlank()) {
            throw new MethodArgumentNotValidException("Name of the genre can't be null or empty");
        }

        MovieEntity movie = new MovieEntity();
        movie.setTitle(createMovieDto.getTitle());
        movie.setDuration(createMovieDto.getDuration());
        movie.setReleaseYear(createMovieDto.getReleaseYear());

        if (createMovieDto.getActors().isEmpty() && createMovieDto.getGenres().isEmpty()) {
            return movie;
        } else {
            if (!createMovieDto.getActors().isEmpty()) {
                movie.setActors(actorRepository.findAllById(createMovieDto.getActors()));
            }
            if (!createMovieDto.getGenres().isEmpty()) {
                movie.setGenres(genreRepository.findAllById(createMovieDto.getGenres()));
            }
        }
        return movie;
    }

    /**
     * @param genres
     */
    public List<String> convertGenreEntitiesToGenreNames(List<GenreEntity> genres) {

        return genres.stream().map(GenreEntity::getName).toList();
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

    /**
     * @param actors
     */
    public List<String> convertActorEntitiesToActorNames(List<ActorEntity> actors) {

        return actors.stream().map(ActorEntity::getName).toList();
    }
    /**
     *
     * @param movieEntity
     * @return
     */
    public MovieDto movieEntityToMovieDto(MovieEntity movieEntity) {

        if (movieEntity == null || movieEntity.getTitle().isBlank()) {
            throw new MethodArgumentNotValidException("Entity can't be null");
        }

        MovieDto movieDto = new MovieDto();

        movieDto.setId(movieEntity.getId());
        movieDto.setTitle(movieEntity.getTitle());
        movieDto.setDuration(movieEntity.getDuration());
        movieDto.setReleaseYear(movieEntity.getReleaseYear());

        // for display set Names of actors and Genres to Movie lists of Genres and Actors
        movieDto.setGenres(convertGenreEntitiesToGenreNames(movieEntity.getGenres()));
        movieDto.setActors(convertActorEntitiesToActorNames(movieEntity.getActors()));

        return movieDto;

    }
}
