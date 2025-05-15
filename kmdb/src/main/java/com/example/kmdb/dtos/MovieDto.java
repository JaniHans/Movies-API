package com.example.kmdb.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import java.util.List;


@Getter
@Setter
public class MovieDto {

    private Long id;
    private String title;
    private int releaseYear;
    private int duration;

    private List<GenreDto> genres = new ArrayList<>();
    private List<ActorDto> actors = new ArrayList<>();



}

