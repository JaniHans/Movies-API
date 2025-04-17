package com.example.kmdb.dtos;

import java.util.List;

public class MovieResponseDTO {

    private Integer id;
    private String title;
    private int releaseYear;
    private int duration;

    private List<GenreResponseDTO> genres;
    private List<ActorResponseDTO> actors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public List<GenreResponseDTO> getGenres() {
        return genres;
    }
    public void setGenres(List<GenreResponseDTO> genres) {
        this.genres = genres;
    }
    public List<ActorResponseDTO> getActors() {
        return actors;
    }
    public void setActors(List<ActorResponseDTO> actors) {
        this.actors = actors;
    }

}

