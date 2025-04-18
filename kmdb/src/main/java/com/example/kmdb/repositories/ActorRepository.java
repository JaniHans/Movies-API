package com.example.kmdb.repositories;

import com.example.kmdb.entities.ActorEntity;
import com.example.kmdb.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    List<ActorEntity> findActorEntitiesByName(String name);
}
