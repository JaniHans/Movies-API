package com.example.kmdb.dtos;

import java.time.LocalDate;
import java.util.List;

public class ActorResponseDTO {

    private Integer id;
    private String name;
    private LocalDate birthDate;
    private List<MovieResponseDTO> movies;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public List<MovieResponseDTO> getMovies() {
        return movies;
    }
    public void setMovies(List <MovieResponseDTO> movies) {
        this.movies = movies;
    }
}
