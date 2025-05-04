package com.example.kmdb.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class MovieDto {

    private Long id;
    private String title;
    private int releaseYear;
    private int duration;

    private List<GenreDto> genres = new ArrayList<>();
    private List<ActorDto> actors = new ArrayList<>();

    public MovieDto(Long id, String title, int releaseYear, int duration) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }


}

