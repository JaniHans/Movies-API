package com.example.kmdb.dtos;

import lombok.Data;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Data

public class ActorDto {

    private final Long id;
    private final String name;
    private final LocalDate birthDate;
    private final Set<MovieDto> movies = new HashSet<>();

}
