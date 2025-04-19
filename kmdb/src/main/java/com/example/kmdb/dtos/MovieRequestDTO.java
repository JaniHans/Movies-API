package com.example.kmdb.dtos;



import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public class MovieRequestDTO {

    private Long id;
    @NotBlank(message = "Movie title is required")
    private String title;
    @Min(value = 1900, message = "Year must be no earlier than 1900")
    @Max(value = 2025, message = "Year must be no later than 2025")
    private int releaseYear;

    @Min(value = 10, message = "Duration must be at least 10 minutes")
    @Max(value = 600, message = "Duration must be no more than 600 minutes")
    private int duration;
    Set<Long> genreIds;
    Set<Long> actorIds;


    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public Set<Long> getGenreIds() {
        return genreIds;
    }

    public Set<Long> getActorIds() {
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

    public void setGenreIds(Set<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public void setActorIds(Set<Long> actorIds) {
        this.actorIds = actorIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
