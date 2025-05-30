package com.example.kmdb.repositories;

import com.example.kmdb.entities.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Page<Actor> findActorEntitiesByNameContainingIgnoreCase(String name, Pageable pageable);
    // This method will not work automatically, because ActorEntity likely does not have a direct movieId field.
    @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m.id = :movieId")
    Page<Actor> findActorEntitiesByMovieId(Long movieId, Pageable pageable);

    Optional<Actor> findActorEntityByNameEqualsIgnoreCase(String name);
}
