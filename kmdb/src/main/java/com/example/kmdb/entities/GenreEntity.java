package com.example.kmdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import lombok.Getter;

import lombok.Setter;

import java.util.ArrayList;

import java.util.List;

@Entity
@Getter

public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres") // inverse side of Movie-Genre
    // This is the inverse side of the Many-to-Many relationship (Movie owns the relation)
    @JsonIgnore
    @Setter
    private List<MovieEntity> movies = new ArrayList<>();

    public void setName(String name) {
        this.name = name.trim();
    }

}
