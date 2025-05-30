package com.example.kmdb.services;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.create.CreateGenreDto;
import com.example.kmdb.entities.Genre;
import com.example.kmdb.entities.Movie;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.mapper.GenreMapper;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final GenreMapper genreMapper;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";

     // CRUD METHODS
    /**
     * POST /api/{genres}: Create a new entity
     * create genre and save it into the database repository
     * @param genreDto - genreDto for creation
     * @return - returns genreDTo by id
     */
    public GenreDto createGenre(CreateGenreDto genreDto) {

        log.info("Genre created : {}, movieIds :{}", genreDto.getName(), genreDto.getMovies());
        //
        Genre genre = genreMapper.createGenreDtoToGenreEntity(genreDto);
        // find by movie IDS
        List<Movie> movies = movieRepository.findAllById(genreDto.getMovies());
        for (Movie movie : movies) {
            movie.getGenres().add(genre);
        }
        genre.setMovies(movies);
        genreRepository.save(genre);
        return genreMapper.genreEntityToGenreDto(genre);

    }
    /**
     * GET /api/{genres}
     * PAGINATION
     * @param pageable - genre pageable object
     * return all genres
     */
    public Page<GenreDto> getAllGenres(Pageable pageable) {
        log.info("Retrieving all genres");
      Page<Genre> genrePage = genreRepository.findAll(pageable);
      return genrePage.map(genreMapper::genreEntityToGenreDto);
    }

    /**
     * GET /api/{genres}/{id}
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public Optional<GenreDto> getGenreById(Long id) {

      return genreRepository.findById(id).map(genreMapper::genreEntityToGenreDto);
    }
    /**
     * PATCH /api/{genres}/{id}
     * @param - genreId and genre new Name
     * modify genres name with the newname, save to genre repository
     */
    public GenreDto modifyGenre(Long genreId, CreateGenreDto genreDto) {

        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + genreId));

        // empty genres from the movies if the new movieID list is empty
        if (genreDto.getMovies().isEmpty()) {

            List<Movie> movies = genre.getMovies();

            for (Movie movie : movies) {
                movie.getGenres().remove(genre);
            }
            genre.getMovies().clear();
        } else {
            // empty genres from movies before adding new movies, replace and delete old basically
            for (Movie movies : genre.getMovies()) {
                movies.getGenres().remove(genre);
            }
            genre.setMovies(movieRepository.findAllById(genreDto.getMovies()));
            for (Movie moviesNew : genre.getMovies()) {
                moviesNew.getGenres().add(genre);
            }
        }
        genre.setName(genreDto.getName());
        genreRepository.save(genre);
        return genreMapper.genreEntityToGenreDto(genre);
    }
    /**
     * DELETE /api/genres/1
     * DELETE /api/genres/1?force=true
     * @param id - genre id
     * @param force - force delete boolean
     */
    public void removeGenre(Long id, boolean force) {

        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + id));
        // if associations exist and force is not true

        if (!genre.getMovies().isEmpty() && !force) {
            throw new DeleteNotAllowedException("Cannot delete "
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
            List<Movie> temporaryMovies = new ArrayList<>(genre.getMovies());
            for (Movie movies : temporaryMovies) {
                // modify the real MovieEntity objects through Hibernate persistence context
                movies.getGenres().remove(genre);
            }
            genreRepository.deleteById(id);
            log.info("Genre deleted");
        }
    }
}
