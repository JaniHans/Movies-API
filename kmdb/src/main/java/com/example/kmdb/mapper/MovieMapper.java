package com.example.kmdb.mapper;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateMovieDto;
import com.example.kmdb.entities.Actor;
import com.example.kmdb.entities.Genre;
import com.example.kmdb.entities.Movie;
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
    public List<String> convertMovieEntitiesToMovieTitles(List<Movie> movies) {

        return movies.stream().map(Movie::getTitle)
                .toList();
    }
    public Movie createMovieDtoToMovieEntity(CreateMovieDto createMovieDto) {

        if (createMovieDto == null || createMovieDto.getTitle().isBlank()) {
            throw new MethodArgumentNotValidException("Name of the genre can't be null or empty");
        }
        Movie movie = new Movie();
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
    public List<String> convertGenreEntitiesToGenreNames(List<Genre> genres) {

        return genres.stream().map(Genre::getName).toList();
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
        actorDto.setMovies(convertMovieEntitiesToMovieTitles(actor.getMovies()));
        return actorDto;
    }
    /**
     * @param actors
     */
    public List<String> convertActorEntitiesToActorNames(List<Actor> actors) {

        return actors.stream().map(Actor::getName).toList();
    }
    /**
     *
     * @param movie
     * @return
     */
    public MovieDto movieEntityToMovieDto(Movie movie) {

        if (movie == null || movie.getTitle().isBlank()) {
            throw new MethodArgumentNotValidException("Entity can't be null");
        }
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDuration(movie.getDuration());
        movieDto.setReleaseYear(movie.getReleaseYear());
        // for display set Names of actors and Genres to Movie lists of Genres and Actors
        movieDto.setGenres(convertGenreEntitiesToGenreNames(movie.getGenres()));
        movieDto.setActors(convertActorEntitiesToActorNames(movie.getActors()));
        return movieDto;
    }
}
