package com.example.kmdb.dtos.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data

public class CreateActorDto {

    @NotBlank(message = "Name can't be blank")
    @Size(min = 2, max = 50, message = "Name must be 2 to 50 characters long")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDate;
    private List<Long> movies = new ArrayList<>();
}
