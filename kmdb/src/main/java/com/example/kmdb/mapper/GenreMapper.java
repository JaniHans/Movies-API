package com.example.kmdb.mapper;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.create.CreateGenreDto;
import com.example.kmdb.entities.Genre;
import com.example.kmdb.entities.Movie;
import com.example.kmdb.exceptions.MethodArgumentNotValidException;
import com.example.kmdb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    private final MovieRepository movieRepository;

    // HELPER METHODS
    /**
     * creates genreEntity from genreCreateDto
     * sets list of movie ids to movie entities within the genre
     * @param createGenreDto - genre
     * @return genre entity
     */
    public Genre createGenreDtoToGenreEntity(CreateGenreDto createGenreDto) {
        if (createGenreDto == null || createGenreDto.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Name of the genre can't be null or empty");
        }
        Genre entity = new Genre();
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
     * @param genre - genre
     * @return genreDTO
     */
    public GenreDto genreEntityToGenreDto(Genre genre) {

        if (genre == null || genre.getName().isBlank()) {
            throw new MethodArgumentNotValidException("Entity can't be null");
        }
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());

        genreDto.setMovies(convertMovieEntitiesToMovieTitles(genre.getMovies()));
        return genreDto;
    }
    /**
     * Returns a list of movie titles that belong to the entity
     * @param movies
     */
    public List<String> convertMovieEntitiesToMovieTitles(List<Movie> movies) {

        return movies.stream().map(Movie::getTitle)
                .toList();
    }
}
