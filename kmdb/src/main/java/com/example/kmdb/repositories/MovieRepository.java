package com.example.kmdb.repositories;

import com.example.kmdb.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
}
