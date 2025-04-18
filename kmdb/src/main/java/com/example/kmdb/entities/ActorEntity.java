package com.example.kmdb.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ActorEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthDate; // ISO 8601 (1980-07-16)

    @ManyToMany(mappedBy = "actors")

    private Set<MovieEntity> movies = new HashSet<>();



    public Long getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    @NotNull
    public String getName() {
        return name;
    }

    public Set<MovieEntity> getMovies() {
        return movies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setMovies(Set<MovieEntity> movies) {
        this.movies = movies;
    }
}
