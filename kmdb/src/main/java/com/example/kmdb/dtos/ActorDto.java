package com.example.kmdb.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Getter
@Setter

public class ActorDto {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private List<MovieDto> movies = new ArrayList<>();

}
