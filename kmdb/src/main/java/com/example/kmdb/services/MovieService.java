package com.example.kmdb.services;

import com.example.kmdb.dtos.MovieRequestDTO;
import com.example.kmdb.dtos.MovieResponseDTO;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";

    public MovieService(GenreRepository genreRepository, MovieRepository movieRepository, ActorRepository actorRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }


    // Movie CRUD endpoints


    private MovieResponseDTO movieToDto(MovieEntity entity) {
        return new MovieResponseDTO(entity.getId(),
                entity.getTitle(),
                entity.getReleaseYear(),
                entity.getDuration());
    }
    /**
     * POST - movies
     * new movies with their title, release year, duration, associated genre, and actors.
     * @return
     */
    public MovieResponseDTO createMovie(MovieRequestDTO dto) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(dto.getTitle());
        entity.setReleaseYear(dto.getReleaseYear());
        entity.setDuration(dto.getDuration());

        movieRepository.save(entity);

        return new MovieResponseDTO(entity.getId(), entity.getTitle(), entity.getReleaseYear(), entity.getDuration());

    }
//You'll need methods to retrieve all movies,
    /**
     * Get - movies
     * return all movies
     */
    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }


// fetch a specific movie by ID,
    /**
     * get movies id
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public MovieEntity getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found by that id: " + id));
    }

    /**
     * filter movies by genre or release year.
     * @return
     */
    /**
     * @param - genreId
     * @return filter a list of movies with the genre
     */
    public Set<MovieResponseDTO> getMoviesByGenre(Long genreId) {

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
    public List<MovieEntity>  filterByYear(int releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }
    /**
     * /Updating a movie's details (including its title, release year, duration, genre, and actors)
     */
    public void modifyMovie(MovieRequestDTO dto) {
        MovieEntity movie = movieRepository.findById(dto.getId())
    }

    /**
     * Don't forget to implement a way to get all actors in a specific movie.
     */
    public Set<ActorEntity> getActorsInMovie(Long id) {
        MovieEntity movie =  movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found by that id: " + id));
        return movie.getActors();

    }
    /**
     * and removing a movie should also be supported.
     */
    public void removeMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {

            throw new RuntimeException("Movie not found");
        }
        movieRepository.deleteById(movieId);
    }

}
