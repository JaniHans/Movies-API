package com.example.kmdb.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // Marks this class as a JPA entity (i.e., a table in the database)
public class GenreEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToMany(mappedBy = "genres") // inverse side of Movie-Genre
    // This is the inverse side of the Many-to-Many relationship (Movie owns the relation)
    private List<MovieEntity> movies = new ArrayList<>();

    public Integer getId() {
        return id;
    }
}
