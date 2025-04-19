package com.example.kmdb.services;

import com.example.kmdb.dtos.GenreRequestDTO;
import com.example.kmdb.dtos.GenreResponseDTO;
import com.example.kmdb.dtos.MovieResponseDTO;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";

    public GenreService(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    private GenreResponseDTO toDto(GenreEntity entity) {
        return new GenreResponseDTO(entity.getId(), entity.getName());
    }
    private MovieResponseDTO movieToDto(MovieEntity entity) {
        return new MovieResponseDTO(entity.getId(),
                entity.getTitle(),
                entity.getReleaseYear(),
                entity.getDuration());
    }
    /**
     * POST /api/{genres}: Create a new entity
     * create genre and save it into the database
     * @param - genre name
     * @return - returns genre by id
     */
    public GenreResponseDTO createGenre(GenreRequestDTO dto) {

        GenreEntity entity = new GenreEntity();
        entity.setName(dto.getName());


        genreRepository.save(entity);
        return new GenreResponseDTO(entity.getId(), entity.getName());

    }

    /**
     * GET /api/{genres}
     * return all genres
     */
    public Set<GenreResponseDTO> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());

    }

    /**
     * GET /api/{genres}/{id}
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public GenreResponseDTO getGenreById(Long id) {
        return toDto(genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + id)));
    }

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
     * PATCH /api/{genres}/{id}
     * @param - genreId and genre new Name
     * modify genres name with the newname, save to genre repository
     */
    public void modifyGenresName(GenreRequestDTO dto) {

        GenreEntity entity = genreRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + dto.getId()));
        entity.setName(dto.getName());
        genreRepository.save(entity);
    }
    /**
     * @param - genre id
     * check if it is present in genrerepository
     * delete and return true
     */
    public void removeGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException(GENRE_NOT_FOUND + id);
        }
        genreRepository.deleteById(id);
    }


}
