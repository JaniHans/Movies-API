package com.example.kmdb.repositories;

import com.example.kmdb.dtos.ActorDto;
import com.example.kmdb.entities.ActorEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    Page<ActorEntity> findActorEntitiesByNameContainingIgnoreCase(String name, Pageable pageable);

    // This method will not work automatically, because ActorEntity likely does not have a direct movieId field.
    @Query("SELECT a FROM ActorEntity a JOIN a.movies m WHERE m.id = :movieId")
    Page<ActorEntity> findActorEntitiesByMovieId(Long movieId, Pageable pageable);
}
