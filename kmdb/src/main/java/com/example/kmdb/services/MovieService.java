package com.example.kmdb.services;

import com.example.kmdb.dtos.ActorResponseDto;
import com.example.kmdb.dtos.MovieRequestDto;
import com.example.kmdb.dtos.MovieResponseDto;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";
    private static final String MOVIE_NOT_FOUND = "Movie not found by id: ";

    public MovieService(GenreRepository genreRepository, MovieRepository movieRepository, ActorRepository actorRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    private void assignGenresAndActors(MovieEntity entity, MovieRequestDto dto) {
        List<GenreEntity> genreList = genreRepository.findAllById(dto.getGenreIds());
        Set<GenreEntity> genreEntities = new HashSet<>(genreList);
        entity.setGenres(genreEntities);

        List<ActorEntity> actorList = actorRepository.findAllById(dto.getActorIds());
        Set<ActorEntity> actorEntities = new HashSet<>(actorList);
        entity.setActors(actorEntities);
    }

    // Movie CRUD endpoints


    private MovieResponseDto movieToDto(MovieEntity entity) {
        return new MovieResponseDto(entity.getId(),
                entity.getTitle(),
                entity.getReleaseYear(),
                entity.getDuration());
    }
    /**
     * POST - movies
     * new movies with their title, release year, duration, associated genre, and actors.
     * @return
     */
    public MovieResponseDto createMovie(MovieRequestDto dto) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(dto.getTitle());
        entity.setReleaseYear(dto.getReleaseYear());
        entity.setDuration(dto.getDuration());

        // helper method to retrieve actors and genres
        assignGenresAndActors(entity, dto);
        // entities

        movieRepository.save(entity);



        return new MovieResponseDto(entity.getId(), entity.getTitle(), entity.getReleaseYear(), entity.getDuration());

    }
//You'll need methods to retrieve all movies,
    /**
     * Get - movies
     * return all movies
     */
    public Set<MovieResponseDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::movieToDto)
                .collect(Collectors.toSet());
    }


// fetch a specific movie by ID,
    /**
     * get movies id
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public MovieResponseDto getMovieById(Long id) {
       return movieToDto(movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found by that id: " + id)));

    }

    /**
     * filter movies by genre or release year.
     * @return
     */
    /**
     * @param - genreId
     * @return filter a list of movies with the genre
     */
    public Set<MovieResponseDto> getMoviesByGenre(Long genreId) {

        genreRepository.findById(genreId).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + genreId));

        return movieRepository.findMovieEntitiesByGenreIds(genreId)
                .stream()
                .map(this::movieToDto)
                .collect(Collectors.toSet());

    }

    /**
     * GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
     * filter or release year.
     */
    public Set<MovieResponseDto> filterByYear(int releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear)
                .stream()
                .map(this::movieToDto)
                .collect(Collectors.toSet());
    }
    /**
     * /Updating a movie's details (including its title, release year, duration, genre, and actors)
     */
    public void modifyMovie(MovieRequestDto dto) {
        MovieEntity movie = movieRepository.
                findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND + dto.getId()));

        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
        // helper method to retrieve actors and genres
        assignGenresAndActors(movie, dto);
    }

    /**
     * Don't forget to implement a way to get all actors in a specific movie.
     */
    public Set<ActorResponseDto> getAllActorsInMovie(Long movieId) {
        MovieResponseDto movie = movieToDto(movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND + movieId)));
        return movie.getActors();

    }
    /**
     * DELETE /api/{entity}/{id}: Delete an entity
     * DELETE /api/movies/1?force=true
     * and removing a movie should also be supported.
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
    /**
     * Implements pagination for GET requests that return multiple entities.
     * GET /api/movies?page=0&size=10
     * returns a page object containing the result and basic pagination information
     * @param page, size
     */
    public Page getEntitiesByPageAndFilterBySize(Integer page, Integer size) {

    }


    }


