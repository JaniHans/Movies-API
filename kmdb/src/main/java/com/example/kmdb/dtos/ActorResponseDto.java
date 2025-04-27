package com.example.kmdb.dtos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ActorResponseDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private Set<MovieResponseDto> movies = new HashSet<>();

    public ActorResponseDto(Long id, String name, LocalDate birthDate) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;

    }

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
    public Set<MovieResponseDto> getMovies() {
        return movies;
    }
    public void setMovies(Set<MovieResponseDto> movies) {
        this.movies = movies;
    }
}
