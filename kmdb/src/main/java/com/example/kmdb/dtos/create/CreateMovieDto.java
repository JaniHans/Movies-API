package com.example.kmdb.dtos.create;


import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateMovieDto {


    @NotNull
    private String title;
    @NotNull
    private int releaseYear;
    @NotNull
    private int duration;
    private List<Long> genresIds = new ArrayList<>();
    private List<Long> actorsIds = new ArrayList<>();
}
