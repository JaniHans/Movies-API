package com.example.kmdb.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter

@Entity

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

}
