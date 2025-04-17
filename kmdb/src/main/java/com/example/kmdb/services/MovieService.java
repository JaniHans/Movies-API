package com.example.kmdb.services;

import com.example.kmdb.dtos.MovieRequestDTO;
import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.repositories.ActorRepository;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieService(GenreRepository genreRepository, MovieRepository movieRepository, ActorRepository actorRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }


    // Movie CRUD endpoints

    /**
     * POST - movies
     * new movies with their title, release year, duration, associated genre, and actors.
     * @return
     */
    public MovieRequestDTO createMovie(String title, int releaseYear, int duration, List<Integer>genreIds, List<Integer>actorIds) {
        MovieRequestDTO movie = new MovieRequestDTO();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDuration(duration);
        movie.setGenreIds(genreIds);
        movie.setActorIds(actorIds);
        return movieRepository.save(movie);
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
    public MovieEntity getMovieById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found by that id: " + id));
    }

    /**
     * filter movies by genre or release year.
     * @return
     */
    public List<MovieEntity> filterByGenre(Integer id) {
        GenreEntity genre = genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre not found by that id: " + id));
       return movieRepository.findByGenres(genre);
    }

    /**
     * GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
     * filter or release year.
     */
    public List<MovieEntity>  filterByYear(int releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }
/**
 * Don't forget to implement a way to get all actors in a specific movie.
 */
 public List<ActorEntity> getActorsInMovie(Integer id) {
        MovieEntity movie =  movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found by that id: " + id));
        return movie.getActors();

 }

    /**
     * /Updating a movie's details (including its title, release year, duration, genre, and actors)
     */
    public void modifyMovie(Integer movieId, String title, int releaseYear, int duration, List<Integer> genreIds, List<Integer> actorIds) {
        MovieEntity movie = getMovieById(movieId);
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDuration(duration);
        movie.setGenres(genreRepository.findAllById(genreIds));
        movie.setActors(actorRepository.findAllById(actorIds));
        movieRepository.save(movie);
    }
    /**
     * and removing a movie should also be supported.
     */
    public void removeMovie(Integer movieId) {
        if (!movieRepository.existsById(movieId)) {

            throw new RuntimeException("Movie not found");
        }
        movieRepository.deleteById(movieId);
    }

}
