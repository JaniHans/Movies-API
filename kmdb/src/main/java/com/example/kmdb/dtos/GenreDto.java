package com.example.kmdb.dtos;


import lombok.Data;


import java.util.List;

@Data
public class GenreDto {

    private String name;

    private List<MovieDto> movies;

}
