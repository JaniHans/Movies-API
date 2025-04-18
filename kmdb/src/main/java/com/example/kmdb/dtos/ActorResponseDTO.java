package com.example.kmdb.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ActorResponseDTO {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private Set<MovieResponseDTO> movies;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public Set<MovieResponseDTO> getMovies() {
        return movies;
    }
    public void setMovies(Set<MovieResponseDTO> movies) {
        this.movies = movies;
    }
}
