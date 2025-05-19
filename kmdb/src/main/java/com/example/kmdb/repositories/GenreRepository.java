package com.example.kmdb.repositories;

import com.example.kmdb.entities.GenreEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {


    // So GenreEntity g means you're selecting from the Java entity class, not a database table.
    // "Select a single GenreEntity object, and give it an alias g."
    // LEFT JOIN You are joining GenreEntity with its movies collection.
    // FETCH tells JPA to immediately load the joined collection (g.movies) in the same query
    // WHERE g.id = :id only return the genre whose id matches the provided :id parameter.
//    @Query("SELECT g FROM GenreEntity g LEFT JOIN FETCH g.movies WHERE g.id = :id")
//    Optional<GenreEntity> findByIdWithMovies(@Param("id") Long id);


    @EntityGraph(attributePaths = {"movies"})
    Optional<GenreEntity> findById(Long id);

    @Override
    @EntityGraph(attributePaths = "movies")
    Page<GenreEntity> findAll(Pageable pageable);
}
