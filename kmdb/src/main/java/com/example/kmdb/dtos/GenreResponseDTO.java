package com.example.kmdb.dtos;

import java.util.List;

public class GenreResponseDTO {

    private Long id;
    private String name;
    private List<MovieResponseDTO> movies;

    public GenreResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MovieResponseDTO> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovies(List<MovieResponseDTO> movies) {
        this.movies = movies;
    }

    public void setName(String name) {
        this.name = name;
    }
}
