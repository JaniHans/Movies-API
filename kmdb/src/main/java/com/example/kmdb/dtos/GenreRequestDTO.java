package com.example.kmdb.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GenreRequestDTO {

    @NotBlank(message = "Genre name is required")
    @Size(min = 4, message = "Length of genre must be at least 4 characters")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
