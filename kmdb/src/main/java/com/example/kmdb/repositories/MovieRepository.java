package com.example.kmdb.repositories;


import com.example.kmdb.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("SELECT m FROM MovieEntity m JOIN m.genres g WHERE g.id = :id")
    Set<MovieEntity> findMovieEntitiesByGenreIds(Long id);

    List<MovieEntity> findByReleaseYear(int releaseYear);

    @Query("SELECT m FROM MovieEntity m JOIN m.actors g WHERE g.id = :id")
   Set<MovieEntity> findByActorsIds(Long Id);
}
