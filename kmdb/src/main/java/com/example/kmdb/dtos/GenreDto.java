package com.example.kmdb.dtos;



import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter

public class GenreDto {
    @Setter
    private Long id;

    private String name;
    @Setter
    private List<String> movies = new ArrayList<>();

    public void setName(String name) {
        this.name = name.trim();
    }

}
