package com.example.kmdb.dtos;

import java.util.List;

public class GenreResponseDTO {

    private Integer id;
    private String name;
    private List<MovieResponseDTO> movies;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MovieResponseDTO> getMovies() {
        return movies;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMovies(List<MovieResponseDTO> movies) {
        this.movies = movies;
    }

    public void setName(String name) {
        this.name = name;
    }
}
