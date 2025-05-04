package com.example.kmdb.dtos.create;

import com.example.kmdb.dtos.MovieDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


@Data
@NoArgsConstructor
public class CreateActorDto {


    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;
    private List<Long> movieIds = new ArrayList<>();

}
