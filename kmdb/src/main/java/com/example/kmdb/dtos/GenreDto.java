package com.example.kmdb.dtos;


import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class GenreDto {

    private Long id;

    private String name;

    private List<Long> movieIds = new ArrayList<>();

}
