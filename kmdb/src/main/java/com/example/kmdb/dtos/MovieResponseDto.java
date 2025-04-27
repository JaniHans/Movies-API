package com.example.kmdb.dtos;

import java.util.HashSet;
import java.util.Set;

public class MovieResponseDto {

    private Long id;
    private String title;
    private int releaseYear;
    private int duration;

    private Set<GenreResponseDto> genres = new HashSet<>();
    private Set<ActorResponseDto> actors = new HashSet<>();

    public MovieResponseDto(Long id, String title, int releaseYear, int duration) {
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
    public Set<GenreResponseDto> getGenres() {
        return genres;
    }
    public void setGenres(Set<GenreResponseDto> genres) {
        this.genres = genres;
    }
    public Set<ActorResponseDto> getActors() {
        return actors;
    }
    public void setActors(Set<ActorResponseDto> actors) {
        this.actors = actors;
    }

}

