package com.example.kmdb.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Getter

public class MovieDto {
    @Setter
    private Long id;
    private String title;
    @Setter
    private int releaseYear;
    @Setter
    private int duration;
    @Setter
    private List<String> genres = new ArrayList<>();
    @Setter
    private List<String> actors = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title.trim();
    }

}

