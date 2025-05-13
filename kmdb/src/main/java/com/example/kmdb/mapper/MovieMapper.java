package com.example.kmdb.mapper;

import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.dtos.create.CreateMovieDto;
import com.example.kmdb.entities.MovieEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto toMovieDto(MovieEntity movieEntity);
    MovieEntity toEntity(MovieDto movieDto);
    MovieEntity toEntity(CreateMovieDto createMovieDto);

    List<MovieDto> toMovieDtoList(List<MovieEntity> movieEntityList);
}
