package com.example.kmdb.dtos.create;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;



import java.util.ArrayList;
import java.util.List;

@Data

public class CreateGenreDto {

    @NotBlank(message = "Genre can't be blank")
    @Size(min = 3, max = 50, message = "Genre must be 3 to 50 characters long")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Genre must contain only letters and spaces")
    private String name;
    private List<Long> movies = new ArrayList<>();

}
