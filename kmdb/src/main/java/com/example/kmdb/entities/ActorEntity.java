package com.example.kmdb.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity

public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthDate; // ISO 8601 (1980-07-16)

    @ManyToMany(mappedBy = "actors")

    private Set<MovieEntity> movies = new HashSet<>();

}
