package com.example.kmdb.dtos.create;

import com.example.kmdb.entities.MovieEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter

public class CreateGenreDto {

    private String name;
    private List<Long> movieIds = new ArrayList<>();
}
