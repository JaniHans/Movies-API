package com.example.kmdb.dtos.create;



import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateMovieDto {


    @NotBlank(message = "Title can't be blank")
    @Size(min = 2, max = 50, message = "Title must be 2 to 50 characters long")

    private String title;
    @NotNull
    @Positive
    private int releaseYear;
    @NotNull
    @Positive
    private int duration;
    private List<Long> genreIds = new ArrayList<>();
    private List<Long> actorIds = new ArrayList<>();
}
