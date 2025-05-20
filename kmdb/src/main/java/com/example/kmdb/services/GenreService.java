package com.example.kmdb.services;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateGenreDto;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import com.example.kmdb.exceptions.DeleteNotAllowedException;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;
import com.example.kmdb.exceptions.ResourceNotFoundException;
import com.example.kmdb.mapper.GenreMapper;
import com.example.kmdb.mapper.MovieMapper;
import com.example.kmdb.repositories.GenreRepository;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    private static final String GENRE_NOT_FOUND = "Genre not found by id: ";


    // HELPER METHODS
    /**
     * creates genreEntity from genreCreateDto
     * sets list of movie ids to movie entities within the genre
     * @param createGenreDto - genre
     * @return genre entity
     */
    private GenreEntity createGenreDtoToGenreEntity(CreateGenreDto createGenreDto) {
        if (createGenreDto == null || createGenreDto.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Name of the genre can't be null or empty");
        }
        GenreEntity entity = new GenreEntity();
        entity.setName(createGenreDto.getName());
        if (createGenreDto.getMovies().isEmpty()) {
        return entity;
        }

        entity.setMovies(movieRepository.findAllById(createGenreDto.getMovies()));
        return entity;
    }
    /**
     * convert entity to genreDto
     * converts list of movie entities to movieDTOs
     * @param genreEntity - genre
     * @return genreDTO
     */
    private GenreDto genreEntityToGenreDto(GenreEntity genreEntity) {

        if (genreEntity == null || genreEntity.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Entity can't be null");
        }
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genreEntity.getId());
        genreDto.setName(genreEntity.getName());

        genreDto.setMovies(convertMovieEntitiesToMovieTitles(genreEntity.getMovies()));
        return genreDto;
    }

    /**
     * Returns a list of movie titles that belong to the entity
     * @param movies
     */
    private List<String> convertMovieEntitiesToMovieTitles(List<MovieEntity> movies) {

        return movies.stream().map(MovieEntity::getTitle)
                .toList();
    }


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
        GenreEntity genre = createGenreDtoToGenreEntity(genreDto);

        // find by movie IDS
        List<MovieEntity> movies = movieRepository.findAllById(genreDto.getMovies());

        for (MovieEntity movie : movies) {
            movie.getGenres().add(genre);
        }

        genre.setMovies(movies);

        genreRepository.save(genre);

        return genreEntityToGenreDto(genre);

    }

    /**
     * GET /api/{genres}
     * PAGINATION
     * @param pageable - genre pageable object
     * return all genres
     */
    public Page<GenreDto> getAllGenres(Pageable pageable) {
        log.info("Retrieving all genres");
      Page<GenreEntity> genrePage = genreRepository.findAll(pageable);

      return genrePage.map(this::genreEntityToGenreDto);
    }

    /**
     * GET /api/{genres}/{id}
     * retrieving a specific genre by ID
     * @param - genre id
     * @return - returns genre by id
     */
    public GenreDto getGenreById(Long id) {

      GenreEntity entity = genreRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + " " + id));


      return genreEntityToGenreDto(entity);

    }

    /**
     * PATCH /api/{genres}/{id}
     * @param - genreId and genre new Name
     * modify genres name with the newname, save to genre repository
     */
    public void modifyGenre(Long genreId, CreateGenreDto genreDto) {

        GenreEntity genre = genreRepository.findById(genreId).orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND + genreId));

        // empty genres from the movies if the new movieID list is empty
        if (genreDto.getMovies().isEmpty()) {

            List<MovieEntity> movies = genre.getMovies();

            for (MovieEntity movie : movies) {
                movie.getGenres().remove(genre);
            }

            genre.getMovies().clear();
        } else {
            // empty genres from movies before adding new movies, replace and delete old basically
            for (MovieEntity movies : genre.getMovies()) {
                movies.getGenres().remove(genre);
            }
            genre.setMovies(movieRepository.findAllById(genreDto.getMovies()));

            for (MovieEntity moviesNew : genre.getMovies()) {
                moviesNew.getGenres().add(genre);
            }
        }


        genre.setName(genreDto.getName());

        genreRepository.save(genre);

    }
    /**
     * DELETE /api/genres/1
     * DELETE /api/genres/1?force=true
     * @param id - genre id
     * @param force - force delete boolean
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
            List<MovieEntity> temporaryMovies = new ArrayList<>(genre.getMovies());

            for (MovieEntity movies : temporaryMovies) {
                // modify the real MovieEntity objects through Hibernate persistence context
                movies.getGenres().remove(genre);
            }

            genreRepository.deleteById(id);
            log.info("Genre deleted");
        }

    }

}
