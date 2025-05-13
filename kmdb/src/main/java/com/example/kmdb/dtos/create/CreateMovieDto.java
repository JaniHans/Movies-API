package com.example.kmdb.dtos.create;


import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateMovieDto {
    private String title;
    private int releaseYear;
    private int duration;
    private final List<Long> genresIds = new ArrayList<>();
    private final List<Long> actorsIds = new ArrayList<>();
}
