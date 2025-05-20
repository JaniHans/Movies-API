package com.example.kmdb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.Getter;

import lombok.Setter;

import java.util.ArrayList;

import java.util.List;

@Entity
@Getter

public class MovieEntity {

    @Id // Marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    @Setter
    private int releaseYear;
    @Setter
    private int duration;

    // Movie ↔ Genre
    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Setter
    private List<GenreEntity> genres = new ArrayList<>();

    // Movie ↔ Actor
    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @Setter
    private List<ActorEntity> actors = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title.trim();
    }

}
