package com.example.kmdb.services;

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


    // Implement CRUD methods for each service:

    /**
     * new movies with their title, release year, duration, associated genre, and actors.
     * @return
     */
    public MovieEntity createMovie(String title, int releaseYear, int duration, List<Integer>genreIds, List<Integer>actorIds) {
        MovieEntity movie = new MovieEntity();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDuration(duration);
        movie.setGenres(genreRepository.findAllById(genreIds));
        movie.setActors(actorRepository.findAllById(actorIds));
        return movieRepository.save(movie);
    }
//You'll need methods to retrieve all movies,
    /**
     * return all movies
     */
    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }


// fetch a specific movie by ID,
    /**
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public MovieEntity getMovieById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found by that id: " + id));
    }

    /**
     * filter by genre
     * @return
     */
    public MovieEntity filterByGenre() {

    }

    /**
     * filter or release year.
     */
    public MovieEntity filterByYear() {

    }
/**
 * Don't forget to implement a way to get all actors in a specific movie.
 */
 public <ActorEntity> getActorsInMovie(MovieEntity movie) {

 }

    /**
     * //Updating a movie's details (including its title, release year, duration, genre, and actors)
     */
    public void modifyMovie(Integer genreId, String newName) {

    }
    /**
     * and removing a movie should also be supported.
     */
    public boolean removeMovie(Integer genreId) {
        if (genreRepository.findById(genreId).isPresent()) {
            genreRepository.deleteById(genreId);
            return true;
        }
        return false;
    }

}
