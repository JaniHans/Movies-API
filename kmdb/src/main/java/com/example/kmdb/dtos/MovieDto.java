package com.example.kmdb.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;

import java.util.List;


@Data
public class MovieDto {
    @Setter(AccessLevel.NONE)
    private Long id;
    private String title;
    private int releaseYear;
    private int duration;

    private List<GenreDto> genres = new ArrayList<>();
    private List<ActorDto> actors = new ArrayList<>();



}

