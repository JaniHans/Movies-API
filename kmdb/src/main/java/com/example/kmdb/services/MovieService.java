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

import java.util.stream.Collectors;

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


    private void assignGenresToMovies(MovieEntity entity, CreateMovieDto dto) {
        // find all genres with the associated movieids
        List<GenreEntity> genreList = genreRepository.findAllById(dto.getGenresIds());
        List<GenreEntity> genreEntities = new ArrayList<>(genreList);
        entity.setGenres(genreEntities);
    }

    private void assignActorsToMovies(MovieEntity entity, CreateMovieDto dto) {
        // find all actors with the associated movieids
        List<ActorEntity> actorList = actorRepository.findAllById(dto.getActorsIds());
        List<ActorEntity> actorEntities = new ArrayList<>(actorList);
        entity.setActors(actorEntities);
    }

    // Movie CRUD endpoints

    /**
     * POST - movies
     * new movies with their title, release year, duration, associated genre, and actors.
     * @return
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
     * Partial TitleMatch Search
     * @param searchCriteria - title search
     * @param pageable - default page 0 , size 10
     * return all movies
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


// fetch a specific movie by ID,
    /**
     * get movies id
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public MovieDto getMovieById(Long id) {
       return movieMapper.toMovieDto(movieRepository
               .findById(id)
               .orElseThrow(() -> new RuntimeException("Movie not found by that id: " + id)));

    }

    /**
     * filter movies by genre or release year.
     * NOT DONE
     * @return
     * @param - genreId
     * @return filter a list of movies with the genre
     */
    public List<MovieDto> getMoviesByGenre(Long genreId) {

        genreRepository.findById(genreId).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + genreId));

        return movieMapper.toMovieDtoList(movieRepository.findMovieEntitiesByGenreIds(genreId));
    }

    /**
     * GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
     * filter or release year.
     */
    public Page<MovieDto> filterByYear(int releaseYear, Pageable pageable) {
        
        return movieRepository.findByReleaseYear(releaseYear, pageable)
                .map(movieMapper::toMovieDto);

    }
    /**
     * /Updating a movie's details (including its title, release year, duration, genre, and actors)
     */
    public void modifyMovie(Long movieId, CreateMovieDto dto) {
        MovieEntity movie = movieRepository.
                findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND + movieId));

        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
        // helper method to retrieve actors and genres
        assignGenresToMovies(movie, dto);
        assignActorsToMovies(movie, dto);

        movieRepository.save(movie);
    }

    /**
     * GET /api/movies/{movieId}/actors: Retrieve all actors starring in a movie
     * Don't forget to implement a way to get all actors in a specific movie.
     */
    public Page<ActorDto> getAllActorsInMovie(Long movieId, Pageable pageable) {
      movieRepository.findById(movieId).orElseThrow(() ->
              new ResourceNotFoundException(MOVIE_NOT_FOUND + movieId));

      return actorRepository.findActorEntitiesByMovieId(movieId, pageable)
              .map(actorMapper::toActorDto);

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

    }


