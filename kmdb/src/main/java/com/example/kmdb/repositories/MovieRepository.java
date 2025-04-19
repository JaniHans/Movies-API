package com.example.kmdb.repositories;

import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("SELECT m FROM MovieEntity m JOIN m.genres g WHERE g.id = :id")
    List<MovieEntity> findMovieEntitiesByGenreIds(Long id);

    List<MovieEntity> findByReleaseYear(int releaseYear);

   List<MovieEntity> findByActors(ActorEntity actor);
}
