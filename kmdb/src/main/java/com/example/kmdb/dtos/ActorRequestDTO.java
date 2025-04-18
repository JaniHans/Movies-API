package com.example.kmdb.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActorRequestDTO {

    private Long id;
    @NotBlank(message = "Actor name is required")
    private String name;
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;


    Set<Long> movieIds = new HashSet<>();

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

    public Set<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(Set<Long> movieIds) {
        this.movieIds = movieIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
