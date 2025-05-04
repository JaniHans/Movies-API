package com.example.kmdb.dtos;

import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Data

public class ActorDto {

    private final Long id;
    private final String name;
    private final LocalDate birthDate;
    private final List<Long> movieIds = new ArrayList<>();

}
