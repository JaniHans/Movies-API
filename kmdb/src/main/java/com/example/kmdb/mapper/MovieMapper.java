package com.example.kmdb.mapper;


import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {GenreMapper.class, ActorMapper.class})
public interface MovieMapper {

}
