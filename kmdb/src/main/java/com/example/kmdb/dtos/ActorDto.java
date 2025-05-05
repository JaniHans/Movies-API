package com.example.kmdb.dtos;


import lombok.Data;



import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Data

public class ActorDto {

    private String name;

    private LocalDate birthDate;

    private List<Long> movieIds = new ArrayList<>();

}
