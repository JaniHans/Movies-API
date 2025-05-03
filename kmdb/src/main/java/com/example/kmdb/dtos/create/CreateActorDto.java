package com.example.kmdb.dtos.create;

import com.example.kmdb.dtos.MovieDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CreateActorDto {
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    private String name;
    @Schema(description = "Name of the Actor", example = "John Travolta")
    private LocalDate birthDate;
    private Set<MovieDto> movies = new HashSet<>();

}
