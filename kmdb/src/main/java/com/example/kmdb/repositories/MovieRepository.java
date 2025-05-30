package com.example.kmdb.repositories;

import com.example.kmdb.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // This selects instances of MovieEntity (aliased as m).
    // This performs an inner join between MovieEntity and its genres collection.
    // Filters the joined genres where the genre ID matches the parameter :id.
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :id")
    Page<Movie> findMovieEntitiesByGenreIds(Long id, Pageable pageable);

    Page<Movie> findByReleaseYear(int releaseYear, Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.actors g WHERE g.id = :id")
    Page<Movie> findByActorsIds(@Param("id") Long id, Pageable pageable);

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
