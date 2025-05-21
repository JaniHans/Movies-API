package com.example.kmdb.dtos.create;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;


import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Data

public class CreateActorDto {

    @NotBlank(message = "Name can't be blank")
    @Size(min = 2, max = 50, message = "Name must be 2 to 50 characters long")
    private String name;

    @NotNull
    private LocalDate birthDate;
    private List<Long> movies = new ArrayList<>();


}
