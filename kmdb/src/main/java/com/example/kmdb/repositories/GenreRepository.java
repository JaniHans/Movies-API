package com.example.kmdb.repositories;

import com.example.kmdb.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}
