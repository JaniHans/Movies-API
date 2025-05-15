package com.example.kmdb.dtos.create;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Data

public class CreateGenreDto {

    @NotEmpty
    private String name;
    private List<Long> movieIds = new ArrayList<>();
}
