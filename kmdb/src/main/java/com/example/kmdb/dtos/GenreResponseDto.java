package com.example.kmdb.dtos;

import java.util.List;

public class GenreResponseDto {

    private Long id;
    private String name;
    private List<MovieResponseDto> movies;

    public GenreResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MovieResponseDto> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovies(List<MovieResponseDto> movies) {
        this.movies = movies;
    }

    public void setName(String name) {
        this.name = name;
    }
}
