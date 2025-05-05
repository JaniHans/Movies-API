package com.example.kmdb.entities;


import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


@Entity
@Getter

public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private LocalDate birthDate; // ISO 8601 (1980-07-16)

    @ManyToMany(mappedBy = "actors")
    @Setter
    private List<MovieEntity> movies = new ArrayList<>();

    private void setId(Long id) {
        // Do nothing
    }

}
