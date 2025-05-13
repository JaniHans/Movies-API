package com.example.kmdb.dtos.create;


import jakarta.validation.constraints.NotNull;

import lombok.Getter;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Getter

public class CreateActorDto {

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;
    private final List<Long> movieIds = new ArrayList<>();

}
