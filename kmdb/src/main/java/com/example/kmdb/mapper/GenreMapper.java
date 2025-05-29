package com.example.kmdb.mapper;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.create.CreateGenreDto;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
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
    public GenreEntity createGenreDtoToGenreEntity(CreateGenreDto createGenreDto) {
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
    public GenreDto genreEntityToGenreDto(GenreEntity genreEntity) {

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
    public List<String> convertMovieEntitiesToMovieTitles(List<MovieEntity> movies) {

        return movies.stream().map(MovieEntity::getTitle)
                .toList();
    }
}
