package com.example.kmdb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Setter
    private LocalDate birthDate; // ISO 8601 (1980-07-16)
    @ManyToMany(mappedBy = "actors")
    @Setter
    private List<Movie> movies = new ArrayList<>();

    public void setName(String name) {
        this.name = name.trim();
    }
}
