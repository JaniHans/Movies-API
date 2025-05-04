package com.example.kmdb.mapper;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.dtos.create.CreateActorDto;
import com.example.kmdb.entities.ActorEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring") // mapper ends spring so we can inject
public interface ActorMapper {

//    List<ActorEntity> actorList = ActorRepository.findAll();
//    ActorResponseDto actorDto = new ActorResponseDto();
//    actorDto.setName(ActorRequestDto.getName());

    ActorDto toDto(ActorEntity actorEntity);
    ActorEntity toEntity(ActorDto actorDto);
    ActorEntity toEntity(CreateActorDto actorDto);

    List<ActorDto> toDtoList(List<ActorEntity> actorEntityList);

}
