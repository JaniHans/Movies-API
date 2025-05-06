package com.example.kmdb.repositories;


import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.entities.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    // This selects instances of MovieEntity (aliased as m).
    // This performs an inner join between MovieEntity and its genres collection.
    // Filters the joined genres where the genre ID matches the parameter :id.
    @Query("SELECT m FROM MovieEntity m JOIN m.genres g WHERE g.id = :id")
    List<MovieEntity> findMovieEntitiesByGenreIds(Long id);

    Page<MovieEntity> findByReleaseYear(int releaseYear, Pageable pageable);


    @Query("SELECT m FROM MovieEntity m JOIN m.actors g WHERE g.id = :id")
    List<MovieEntity> findByActorsIds(Long id);

    Page<MovieEntity> findByActorsId(Long actorId, Pageable pageable);
    Page<MovieEntity> findByGenreId(Long actorId, Pageable pageable);

    Page<MovieEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
