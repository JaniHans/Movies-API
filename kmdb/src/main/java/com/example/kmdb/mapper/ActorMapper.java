package com.example.kmdb.mapper;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.create.CreateActorDto;
import com.example.kmdb.entities.ActorEntity;
import org.mapstruct.Mapper;


import java.util.List;
@Mapper(componentModel = "spring") // mapper ends spring so we can inject
public interface ActorMapper {

    ActorDto toActorDto(ActorEntity actorEntity);
    ActorEntity toEntity(ActorDto actorDto);
    ActorEntity toEntity(CreateActorDto actorDto);

    List<ActorDto> toActorDtoList(List<ActorEntity> actorEntityList);

}
