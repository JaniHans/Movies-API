package com.example.kmdb.dtos;

import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;

import java.util.List;

public class MovieRequestDTO {

    private String title;
    private int releaseYear;
    private int duration;
    List<Integer> genreIds;
    List<Integer> actorIds;


    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public List<Integer> getActorIds() {
        return actorIds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public void setActorIds(List<Integer> actorIds) {
        this.actorIds = actorIds;
    }
}
