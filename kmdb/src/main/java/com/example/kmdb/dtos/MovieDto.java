package com.example.kmdb.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class MovieDto {

    private Long id;
    private String title;
    private int releaseYear;
    private int duration;

    private Set<GenreDto> genres = new HashSet<>();
    private Set<ActorDto> actors = new HashSet<>();

    public MovieDto(Long id, String title, int releaseYear, int duration) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public Set<GenreDto> getGenres() {
        return genres;
    }
    public void setGenres(Set<GenreDto> genres) {
        this.genres = genres;
    }
    public Set<ActorDto> getActors() {
        return actors;
    }
    public void setActors(Set<ActorDto> actors) {
        this.actors = actors;
    }

}

