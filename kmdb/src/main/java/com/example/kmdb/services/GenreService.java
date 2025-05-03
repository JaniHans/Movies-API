package com.example.kmdb.services;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.mapper.ActorMapper;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";
    private final ActorMapper actorMapper;


//    private GenreResponseDto toDto(GenreEntity entity) {
//        return new GenreResponseDto(entity.getId(), entity.getName());
//    }
//    private MovieResponseDto movieToDto(MovieEntity entity) {
//        return new MovieResponseDto(entity.getId(),
//                entity.getTitle(),
//                entity.getReleaseYear(),
//                entity.getDuration());
//    }
    /**
     * POST /api/{genres}: Create a new entity
     * create genre and save it into the database
     * @param - genre name
     * @return - returns genre by id
     */
    public GenreDto createGenre(GenreRequestDto dto) {

        GenreEntity entity = new GenreEntity();
        entity.setName(dto.getName());


        genreRepository.save(entity);
        return new GenreDto(entity.getId(), entity.getName());

    }

    /**
     * GET /api/{genres}
     * return all genres
     */
    public Set<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(actorMapper::toDto)
                .collect(Collectors.toSet());

    }

    /**
     * GET /api/{genres}/{id}
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public GenreDto getGenreById(Long id) {
        return toDto(genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + id)));
    }

    /**
     * GET /api/movies?genre={genreId}: Retrieve movies filtered by genre
     * @param - genreId
     * @return filter a list of movies with the genre
     */
   public Set<MovieDto> filterMoviesByGenre(Long genreId) {

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
    public void modifyGenresName(GenreRequestDto dto) {

        GenreEntity entity = genreRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + dto.getId()));
        entity.setName(dto.getName());
        genreRepository.save(entity);
    }
    /**
     * DELETE /api/genres/1
     * DELETE /api/genres/1?force=true
     * @param - genre id
     * check if it is present in genrerepository
     * delete and return true
     */
    public void removeGenre(Long id, boolean force) {

        GenreEntity genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + id));
        // if associations exist and force is not true
        if (!genre.getMovies().isEmpty() && !force) {
            throw new DeleteNotAllowedException("Cannot delete genre "
                    + genre.getName()
                    + " because it has "
                    + genre.getMovies().size()
                    + " associated movies.");
        }

        if (genre.getMovies().isEmpty()) {
            genreRepository.deleteById(id);
        }

        // force break associations

        if (force) {

            // reference to movie entity objects
            Set<MovieEntity> temporaryMovies = new HashSet<>(genre.getMovies());

            for (MovieEntity movies : temporaryMovies) {
                // modify the real MovieEntity objects through Hibernate persistence context
                movies.getGenres().remove(genre);
            }

            genreRepository.deleteById(id);
        }


    }
    /**
     * Implements pagination for GET requests that return multiple entities.
     * GET /api/actors?page=0&size=10
     * returns a page object containing the result and basic pagination information
     * @param page, size
     */
    public Page getEntitiesByPageAndFilterBySize(Integer page, Integer size) {

    }


}
