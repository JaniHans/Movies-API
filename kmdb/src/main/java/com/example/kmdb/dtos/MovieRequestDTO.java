package com.example.kmdb.dtos;


import com.example.kmdb.entities.MovieEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class MovieRequestDTO {

    @NotBlank(message = "Movie title is required")
    private String title;
    @Min(value = 1900, message = "Year must be no earlier than 1900")
    @Max(value = 2025, message = "Year must be no later than 2025")
    private int releaseYear;

    @Min(value = 10, message = "Duration must be at least 10 minutes")
    @Max(value = 600, message = "Duration must be no more than 600 minutes")
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
