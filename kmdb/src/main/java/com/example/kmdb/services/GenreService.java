package com.example.kmdb.services;

import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    public GenreService(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }
    /**
     * create genre and save it into the database
     * @param - genre name
     * @return - returns genre by id
     */
    public GenreEntity createGenre(String name) {
        GenreEntity genre = new GenreEntity(); // create genreentity plain object
        genre.setName(name); // updates the objects field just in memory
        return genreRepository.save(genre); // JPA inserts it into the database, returns saved entity with generated id
    }

    /**
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public GenreEntity getGenreById(Integer id) {
        return genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre not found by that id: " + id));
    }

    /**
     * @param - genreId
     * @return filter a list of movies with the genre
     */
   public List<MovieEntity> getMoviesByGenre(GenreEntity genre) {
       return movieRepository.findByGenres(genre); // otsene andmebaasi päring

   }
    /**
     * @param - genreId and genre new Name
     * modify genres name with the newname, save to genre repository
     */
    public void modifyGenresName(Integer genreId, String newName) {
        GenreEntity genre = getGenreById(genreId);
        genre.setName(newName);
        genreRepository.save(genre);

    }
    /**
     * @param - genre id
     * check if it is present in genrerepository
     * delete and return true
     */
    public void removeGenre(Integer genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new RuntimeException("Genre not found");
        }
        genreRepository.deleteById(genreId);
    }

    /**
     * return all genres
     */
    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }
}
