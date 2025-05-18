package com.example.kmdb.repositories;


import com.example.kmdb.dtos.MovieDto;
import com.example.kmdb.entities.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    // This selects instances of MovieEntity (aliased as m).
    // This performs an inner join between MovieEntity and its genres collection.
    // Filters the joined genres where the genre ID matches the parameter :id.
    @Query("SELECT m FROM MovieEntity m JOIN m.genres g WHERE g.id = :id")
    Page<MovieEntity> findMovieEntitiesByGenreIds(Long id, Pageable pageable);

    Page<MovieEntity> findByReleaseYear(int releaseYear, Pageable pageable);


    @Query("SELECT m FROM MovieEntity m JOIN m.actors g WHERE g.id = :id")
    Page<MovieEntity> findByActorsIds(@Param("actorId") Long id, Pageable pageable);


    @Query("SELECT m FROM MovieEntity m JOIN m.genres g WHERE g.id = :genreId")
    Page<MovieEntity> findByGenresId(@Param("genreId") Long actorId, Pageable pageable);

    Page<MovieEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);


}
