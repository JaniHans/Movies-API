package com.example.kmdb.mapper;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.create.CreateGenreDto;
import com.example.kmdb.entities.GenreEntity;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toGenreDto(GenreEntity genreEntity);
    GenreEntity toEntity(GenreDto genreDto);
    GenreEntity toEntity(CreateGenreDto createGenreDto);

    List<GenreDto> toDtoList(List<GenreEntity> actor);
}
