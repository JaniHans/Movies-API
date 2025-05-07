package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateMovieDto;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.mapper.ActorMapper;
import com.example.kmdb.mapper.MovieMapper;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MovieService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    private final MovieMapper movieMapper;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";
    private static final String MOVIE_NOT_FOUND = "Movie not found by id: ";
    private final ActorMapper actorMapper;

    /**
     * helper method to find and assign genres that are associated with the movies
     */
    private void assignGenresToMovies(MovieEntity entity, CreateMovieDto dto) {
        // find all genres with the associated movieids
        List<GenreEntity> genreList = genreRepository.findAllById(dto.getGenresIds());
        List<GenreEntity> genreEntities = new ArrayList<>(genreList);
        entity.setGenres(genreEntities);
    }
    /**
     * helper method to find and assign actors that are associated with the movies
     */
    private void assignActorsToMovies(MovieEntity entity, CreateMovieDto dto) {
        // find all actors with the associated movieids
        List<ActorEntity> actorList = actorRepository.findAllById(dto.getActorsIds());
        List<ActorEntity> actorEntities = new ArrayList<>(actorList);
        entity.setActors(actorEntities);
    }

    // Movie CRUD endpoints

    /**
     * Method for POST endpoint - create movie
     * creates new movie with their title, release year, duration, associated genres, and actors.
     * saves to repository
     * uses movieMapper interface to convert entity to DTO
     * @param createMovieDto - DTO object of movie for creating and modifying movies
     * @return MovieDto to controller
     */
    public MovieDto createMovie(CreateMovieDto createMovieDto) {
        MovieEntity entity = movieRepository.save(movieMapper.toEntity(createMovieDto));

        // helper method to retrieve actors and genres
        assignActorsToMovies(entity, createMovieDto);
        assignGenresToMovies(entity, createMovieDto);

        // entities

        movieRepository.save(entity);
        return movieMapper.toMovieDto(entity);
    }

    /**
     * Get all movies, including pagination and search
     * using mapper interface method to convert entity to dto
     * @param searchCriteria - title search
     * @param pageable - default page 0 , size 10, and searchCriteria if present
     * @return movies
     */
    public Page<MovieDto> getAllMovies(String searchCriteria, Pageable pageable) {

        if (searchCriteria.isEmpty()) {
            // create regular pageable with page and size
            return movieRepository.findAll(pageable)
                    .map(movieMapper::toMovieDto);
        }
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title").ascending());

        return movieRepository.findByTitleContainingIgnoreCase(searchCriteria, pageable)
                .map(movieMapper::toMovieDto);
    }

    /**
     * get movie by id
     * using mapper interface method to convert entity to dto
     * retrieving a specific genre by ID
     * @param movieId - movie id
     * @return - returns single item of movieDto
     */
    public MovieDto getMovieById(Long movieId) {
       return movieMapper.toMovieDto(movieRepository
               .findById(movieId)
               .orElseThrow(() -> new RuntimeException("Movie not found by that id: " + movieId)));

    }
    /**
     * get and filter movies by genre or release year using pagination.
     * @param genreId - genre id
     * @param pageable - pageable object with size and page
     * @return page type of MovieDTOs filtered by genre
     */
    public Page<MovieDto> getMoviesByGenre(Long genreId, Pageable pageable) {

        return movieRepository.findMovieEntitiesByGenreIds(genreId, pageable)
                .map(movieMapper::toMovieDto);

    }

    /**
     * GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
     * @param releaseYear - movie release year
     * @param pageable - pageable object with size and page
     * filter or release year.
     */
    public Page<MovieDto> filterByYear(int releaseYear, Pageable pageable) {
        
        return movieRepository.findByReleaseYear(releaseYear, pageable)
                .map(movieMapper::toMovieDto);

    }
    /**
     * PATCH
     * Modify movies including its title, release year, duration, genre, and actors
     * @param movieId - id of the movie
     * @param dto - DTO object of movie for creating and modifying movies
     */
    public void modifyMovie(Long movieId, CreateMovieDto dto) {
        MovieEntity movie = movieRepository
                .findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND + movieId));

        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
        // helper method to retrieve and save actors
        assignGenresToMovies(movie, dto);
        // helper method to retrieve and save genres
        assignActorsToMovies(movie, dto);

        movieRepository.save(movie);
    }

    /**
     * GET /api/movies/{movieId}/actors: Retrieves all actors starring in a movie
     * @param movieId - id of the movie
     * @param pageable - pageable object including parameters for pagination
     * @return - page type of movieDto
     */
    public Page<ActorDto> getAllActorsInMovie(Long movieId, Pageable pageable) {
      movieRepository.findById(movieId).orElseThrow(() ->
              new ResourceNotFoundException(MOVIE_NOT_FOUND + movieId));


      return actorRepository.findActorEntitiesByMovieId(movieId, pageable)
              .map(actorMapper::toActorDto);

    }
    /**
     * DELETE /api/{entity}/{id}: Delete an entity
     * deletes movie by id if there is no associated genres and actors
     * DELETE /api/movies/1?force=true
     * forces delete regardless
     * @param movieId - id of the movie
     * @param force - boolean value whether to override delete
     */
    public void removeMovie(Long movieId, boolean force) {

        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND + movieId));

        // boolean flags for correct error message

        boolean hasActors = !movie.getActors().isEmpty();
        boolean hasGenres = !movie.getGenres().isEmpty();

        if ((hasActors || hasGenres && !force)) {

            StringBuilder message = new StringBuilder("Cannot delete movie ");
            message.append(movie.getTitle())
                    .append(" because it has ");

            if (hasActors) {
                message.append(movie.getActors().size())
                        .append(" associated actors");
            }

            if (hasActors && hasGenres) {
                message.append(" and ");
            }

            if (hasGenres) {
                message.append(movie.getGenres().size())
                        .append(" associated genre(s).");
            }
            throw new DeleteNotAllowedException(message.toString());

        }
        movieRepository.deleteById(movieId);
    }
    }


