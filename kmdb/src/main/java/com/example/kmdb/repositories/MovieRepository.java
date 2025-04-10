package com.example.kmdb.repositories;

import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.GenreEntity;
import com.example.kmdb.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

    List<MovieEntity> findByGenres(GenreEntity genre);

    List<MovieEntity> findByReleaseYear(int releaseYear);

   List<MovieEntity> findByActors(ActorEntity actor);
}
