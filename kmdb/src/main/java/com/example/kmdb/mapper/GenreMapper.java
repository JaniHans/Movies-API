package com.example.kmdb.mapper;

import com.example.kmdb.dtos.GenreDto;
import com.example.kmdb.entities.GenreEntity;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GenreMapper {


    GenreDto toDto(GenreEntity actor);

    Set<GenreDto> toDtoList(Set<GenreDto> actor);
}
