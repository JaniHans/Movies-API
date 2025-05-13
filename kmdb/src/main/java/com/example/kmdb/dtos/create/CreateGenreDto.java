package com.example.kmdb.dtos.create;



import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter

public class CreateGenreDto {

    private String name;
    private final List<Long> movieIds = new ArrayList<>();
}
