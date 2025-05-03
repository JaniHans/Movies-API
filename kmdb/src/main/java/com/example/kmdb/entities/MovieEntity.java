package com.example.kmdb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

import java.util.Set;
@Entity
@Getter
@Setter
public class MovieEntity {

    @Id // Marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int releaseYear;

    private int duration;

    // Movie ↔ Genre
    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<GenreEntity> genres = new HashSet<>();

    // Movie ↔ Actor
    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )

    private Set<ActorEntity> actors = new HashSet<>();

// Lombok no need gfor setters and getters
//    public String getTitle() {
//        return title;
//    }
//
//    public int getReleaseYear() {
//        return releaseYear;
//    }
//
//    public int getDuration() {
//        return duration;
//    }
//
//    public Set<ActorEntity> getActors() {
//        return actors;
//    }
//
//    public Set<GenreEntity> getGenres() {
//        return genres;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setReleaseYear(int releaseYear) {
//        this.releaseYear = releaseYear;
//    }
//
//    public void setDuration(int duration) {
//        this.duration = duration;
//    }
//
//    public void setActors(Set<ActorEntity> actors) {
//        this.actors = actors;
//    }
//
//    public void setGenres(Set<GenreEntity> genres) {
//        this.genres = genres;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
}
