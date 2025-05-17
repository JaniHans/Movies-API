package com.example.kmdb.mapper;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.create.CreateGenreDto;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "movies", ignore = true)
    // Avoids circular reference
    GenreDto toGenreDto(GenreEntity genreEntity);

    GenreEntity toEntity(CreateGenreDto createGenreDto);

    List<MovieEntity> toMovieEntityList(List<Long> movieIds)
    List<GenreDto> toDtoList(List<GenreEntity> actor);
}
