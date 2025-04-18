package com.example.kmdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Marks this class as a JPA entity (i.e., a table in the database)
public class GenreEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToMany(mappedBy = "genres") // inverse side of Movie-Genre
    // This is the inverse side of the Many-to-Many relationship (Movie owns the relation)
    @JsonIgnore
    private Set<MovieEntity> movies = new HashSet<>();




    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<MovieEntity> getMovies() {
        return movies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovies(Set<MovieEntity> movies) {
        this.movies = movies;
    }
}
