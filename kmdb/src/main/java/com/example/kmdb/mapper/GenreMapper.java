package com.example.kmdb.mapper;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MovieMapper.class)
public interface GenreMapper {


}
