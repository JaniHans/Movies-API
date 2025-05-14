package com.example.kmdb.dtos.create;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter

public class CreateGenreDto {
    @NotNull
    @NotEmpty
    private String name;
    private final List<Long> movieIds = new ArrayList<>();
}
