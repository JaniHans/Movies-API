package com.example.kmdb.services;

import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    //Implement CRUD methods for each service:


//    Your API should handle creating new genres,

    public GenreEntity createGenre(String name) {
        GenreEntity genre = new GenreEntity();
        genre.setName(name);
        return genreRepository.save(genre);
    }

//    retrieving all genres or a specific genre by ID,
//    and fetching all movies in a specific genre.
//    You'll also need to implement methods to modify an existing genre's name
//    and to remove a genre from the database.
}
