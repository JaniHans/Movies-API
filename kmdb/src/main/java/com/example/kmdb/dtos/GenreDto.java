package com.example.kmdb.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GenreDto {

    private Long id;
    private String name;
    private List<MovieDto> movies;

    public GenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MovieDto> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovies(List<MovieDto> movies) {
        this.movies = movies;
    }

    public void setName(String name) {
        this.name = name;
    }
}
