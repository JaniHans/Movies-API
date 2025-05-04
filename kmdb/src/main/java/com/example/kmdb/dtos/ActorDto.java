package com.example.kmdb.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Data

public class ActorDto {

    private Long id;
    private final String name;
    private final LocalDate birthDate;
    private final List<Long> movieIds = new ArrayList<>();

}
