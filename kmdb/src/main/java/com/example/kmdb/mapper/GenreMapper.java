package com.example.kmdb.mapper;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.dtos.create.CreateGenreDto;
import com.example.kmdb.entities.GenreEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper(componentModel = "spring")
public interface GenreMapper {


    GenreDto toDto(GenreEntity actor);
    GenreEntity toEntity(GenreDto genreDto);
    GenreEntity toEntity(CreateGenreDto createGenreDto);

    List<GenreDto> toDtoList(List<GenreEntity> actor);
}
