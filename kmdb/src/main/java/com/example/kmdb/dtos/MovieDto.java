package com.example.kmdb.dtos;


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

    private List<String> genres = new ArrayList<>();
    private List<String> actors = new ArrayList<>();



}

