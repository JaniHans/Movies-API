package com.example.kmdb.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GenreDto {

    private Long id;

    private String name;

    private List<Long> movieIds = new ArrayList<>();

}
