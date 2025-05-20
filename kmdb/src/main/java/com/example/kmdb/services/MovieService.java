package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateMovieDto;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;
import com.example.kmdb.exceptions.ResourceNotFoundException;

import com.example.kmdb.mapper.MovieMapper;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    private final MovieMapper movieMapper;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";
    private static final String MOVIE_NOT_FOUND = "Movie not found by id: ";
    private static final String ACTOR_NOT_FOUND = "Actor not found by id: ";


//    // HELPER METHODS
//    /**
//     * convert entity to genreDto
//     * converts list of movie entities to movieDTOs
//     * @param genreEntity - genre
//     * @return genreDTO
//     */
//    private GenreDto genreEntityToGenreDto(GenreEntity genreEntity) {
//
//        if (genreEntity == null || genreEntity.getName().isBlank()) {
//            throw new MethodArgumentNotValidException("Entity can't be null");
//        }
//        GenreDto genreDto = new GenreDto();
//        genreDto.setId(genreEntity.getId());
//        genreDto.setName(genreEntity.getName());
//
//        genreDto.setMovieTitles(convertMovieEntitiesToMovieTitles(genreEntity.getMovies()));
//        return genreDto;
//    }


    /**
     * @param movies
     */
    private List<String> convertMovieEntitiesToMovieTitles(List<MovieEntity> movies) {

        return movies.stream().map(MovieEntity::getTitle)
                .toList();
    }

    private MovieEntity createMovieDtoToMovieEntity(CreateMovieDto createMovieDto) {

        if (createMovieDto == null || createMovieDto.getTitle().isBlank()) {
            throw new MethodArgumentNotValidException("Name of the genre can't be null or empty");
        }

        MovieEntity movie = new MovieEntity();
        movie.setTitle(createMovieDto.getTitle());
        movie.setDuration(createMovieDto.getDuration());
        movie.setReleaseYear(createMovieDto.getReleaseYear());

        if (createMovieDto.getActorIds().isEmpty() && createMovieDto.getGenreIds().isEmpty()) {
            return movie;
        } else {
            if (!createMovieDto.getActorIds().isEmpty()) {
                movie.setActors(actorRepository.findAllById(createMovieDto.getActorIds()));
            }
            if (!createMovieDto.getGenreIds().isEmpty()) {
                movie.setGenres(genreRepository.findAllById(createMovieDto.getGenreIds()));
            }
        }
        return movie;
    }

    /**
     * @param genres
     */
    private List<String> convertGenreEntitiesToGenreNames(List<GenreEntity> genres) {

       return genres.stream().map(GenreEntity::getName).toList();
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

    /**
     * @param actors
     */
    private List<String> convertActorEntitiesToActorNames(List<ActorEntity> actors) {

        return actors.stream().map(ActorEntity::getName).toList();
    }
    /**
     *
     * @param movieEntity
     * @return
     */
    private MovieDto movieEntityToMovieDto(MovieEntity movieEntity) {

        if (movieEntity == null || movieEntity.getTitle().isBlank()) {
            throw new MethodArgumentNotValidException("Entity can't be null");
        }

        MovieDto movieDto = new MovieDto();

        movieDto.setId(movieEntity.getId());
        movieDto.setTitle(movieEntity.getTitle());
        movieDto.setDuration(movieDto.getDuration());
        movieDto.setReleaseYear(movieEntity.getReleaseYear());

        // for display set Names of actors and Genres to Movie lists of Genres and Actors
        movieDto.setGenres(convertGenreEntitiesToGenreNames(movieEntity.getGenres()));
        movieDto.setActors(convertActorEntitiesToActorNames(movieEntity.getActors()));

        return movieDto;

    }

    // MOVIE CRUD ENDPOINTS

    /**
     * Method for POST endpoint - create movie
     * creates new movie with their title, release year, duration, associated genres, and actors.
     * saves to repository
     * uses movieMapper interface to convert entity to DTO
     * @param createMovieDto - DTO object of movie for creating and modifying movies
     * @return MovieDto to controller
     */
    public MovieDto createMovie(CreateMovieDto createMovieDto) {

        log.info("Movie created : {}", createMovieDto.getTitle());

        MovieEntity movie = createMovieDtoToMovieEntity(createMovieDto);

        // find by actor ids
        List<GenreEntity> genres = genreRepository.findAllById(createMovieDto.getGenreIds());
        List<ActorEntity> actors = actorRepository.findAllById(createMovieDto.getActorIds());
        // find by genre ids

        // set Movies to Genre entities for bidirectional relationships

        for (GenreEntity genre : genres) {
            genre.getMovies().add(movie);
        }
        // set Movies to Actor entities for bidirectional relationships
        for (ActorEntity actor : actors) {
            actor.getMovies().add(movie);
        }

        movie.setGenres(genres);
        movie.setActors(actors);

        movieRepository.save(movie);

        return movieEntityToMovieDto(movie);
        // return movieDto
    }

    /**
     * Get all movies, including pagination and search
     * using mapper interface method to convert entity to dto
     * @param pageable - default page 0 , size 10, and searchCriteria if present
     * @return movies
     */
    public Page<MovieDto> getAllMovies(Pageable pageable) {

        log.info("Retrieving all movies");
        Page<MovieEntity> movies = movieRepository.findAll(pageable);
        return movies.map(this::movieEntityToMovieDto);
        }

    /**
     * get movie by id
     * using mapper interface method to convert entity to dto
     * retrieving a specific genre by ID
     * @param movieId - movie id
     * @return - returns single item of movieDto
     */
    public MovieDto getMovieById(Long movieId) {

        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND + " " + movieId));

        return movieEntityToMovieDto(movie);

    }

    /**
     * Get all movies, including pagination and search
     * using mapper interface method to convert entity to dto
     * @param searchCriteria - title search
     * @param pageable - default page 0 , size 10, and searchCriteria if present
     * @return movies
     */
    public Page<MovieDto> getAllMoviesSpecial(String searchCriteria, Pageable pageable) {

        if (searchCriteria.isEmpty()) {
            // create regular pageable with page and size
            return movieRepository.findAll(pageable)
                    .map(this::movieEntityToMovieDto);
        }
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title").ascending());

        return movieRepository.findByTitleContainingIgnoreCase(searchCriteria, pageable)
                .map(this::movieEntityToMovieDto);
    }


    /**
     * get and filter movies by genre or release year using pagination.
     * @param genreId - genre id
     * @param pageable - pageable object with size and page
     * @return page type of MovieDTOs filtered by genre
     */
    public Page<MovieDto> getMoviesByGenre(Long genreId, Pageable pageable) {

        return movieRepository.findMovieEntitiesByGenreIds(genreId, pageable)
                .map(this::movieEntityToMovieDto);

    }

    /**
     * GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
     * @param releaseYear - movie release year
     * @param pageable - pageable object with size and page
     * filter or release year.
     */
    public Page<MovieDto> filterByYear(int releaseYear, Pageable pageable) {
        
        return movieRepository.findByReleaseYear(releaseYear, pageable)
                .map(this::movieEntityToMovieDto);

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

        // remove movie from the child owner - bidirectional relationship
        for (ActorEntity actor : movie.getActors()) {
            actor.getMovies().remove(movie);
        }
        // remove movie one way or another from the child owner - bidirectional relationship
        for (GenreEntity genre : movie.getGenres()) {
            genre.getMovies().remove(movie);
        }

        if (!dto.getActorIds().isEmpty()) {
        movie.setActors(actorRepository.findAllById(dto.getActorIds()));
        for (ActorEntity actor : movie.getActors()) {
            actor.getMovies().add(movie);
        }
        }

        if (!dto.getGenreIds().isEmpty()) {
            movie.setGenres(genreRepository.findAllById(dto.getGenreIds()));
            for (GenreEntity genre : movie.getGenres()) {
                genre.getMovies().add(movie);
            }
        }

        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
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
              .map(this::actorEntityToActorDto);

    }

    /**
     * fetch all movies an actor has appeared in.
     * GET /api/movies?actor={Actor.id}
     * Pagination
     * @param actorId -actor id
     * @param pageable - pageable object
     * @return MovieDto of page type
     */
    public Page<MovieDto> getMoviesWhereActorAppeared(Long actorId, Pageable pageable) {
        actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(ACTOR_NOT_FOUND + actorId));

        return movieRepository.findByActorsIds(actorId, pageable)
                .map(this::movieEntityToMovieDto);

    }

    /**
     * deletes movie by id if there is no associated genres and actors
     * DELETE /api/{entity}/{id}: Delete an entity
     * DELETE /api/movies/1?force=true
     * forces delete regardless
     * @param movieId - id of the movie
     * @param force - boolean value whether regular delete
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
        log.info("Movie deleted");
    }
    }


