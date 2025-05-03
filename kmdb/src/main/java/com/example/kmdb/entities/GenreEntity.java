package com.example.kmdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
public class GenreEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToMany(mappedBy = "genres") // inverse side of Movie-Genre
    // This is the inverse side of the Many-to-Many relationship (Movie owns the relation)
    @JsonIgnore
    private Set<MovieEntity> movies = new HashSet<>();


}
