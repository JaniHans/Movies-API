package com.example.kmdb.services;

import com.example.kmdb.dtos.GenreRequestDTO;
import com.example.kmdb.dtos.GenreResponseDTO;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    public GenreService(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }
    /**
     * POST /api/{genres}: Create a new entity
     * create genre and save it into the database
     * @param - genre name
     * @return - returns genre by id
     */
    public GenreResponseDTO createGenre(GenreRequestDTO dto) {
        GenreEntity genre = new GenreEntity();
        genre.setName(dto.getName());
        genreRepository.save(genre);
        GenreResponseDTO dtoGenre = new GenreResponseDTO();
        dtoGenre.setName(genre.getName());
        dtoGenre.setId(genre.getId());
        return dtoGenre;

    }

    /**
     * GET /api/{genres}
     * return all genres
     */
    public List<GenreResponseDTO> getAllGenres() {
        List<GenreEntity> entities = genreRepository.findAll();

        List<GenreResponseDTO> genres = new ArrayList<>();

        for (GenreEntity entity : entities) {
            GenreResponseDTO genre = new GenreResponseDTO();
            genre.setName(entity.getName());
            genre.setId(entity.getId());
            genres.add(genre);
        }

        return genres;

    }

    /**
     * GET /api/{genres}/{id}
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public GenreResponseDTO getGenreById(Integer id) {
        GenreEntity entity = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre not found by id: " + id));
        GenreResponseDTO genre = new GenreResponseDTO();
        genre.setName(entity.getName());
        genre.setId(entity.getId());

        return genre;
    }

    /**
     * @param - genreId
     * @return filter a list of movies with the genre
     */
   public List<MovieEntity> getMoviesByGenre(GenreEntity genre) {
       return movieRepository.findByGenres(genre); // otsene andmebaasi päring

   }
    /**
     * PATCH /api/{genres}/{id}
     * @param - genreId and genre new Name
     * modify genres name with the newname, save to genre repository
     */
    public void modifyGenresName(Integer genreId, String newName) {

        GenreEntity entity = genreRepository.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("Genre not found by id: " + genreId));
        entity.setName(newName);
        genreRepository.save(entity);
    }
    /**
     * @param - genre id
     * check if it is present in genrerepository
     * delete and return true
     */
    public void removeGenre(Integer genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResourceNotFoundException("Genre with id: " + genreId + " not found");
        }
        genreRepository.deleteById(genreId);
    }


}
