package com.example.kmdb.repositories;

import com.example.kmdb.entities.ActorEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    List<ActorEntity> findActorEntitiesByNameContainingIgnoreCase(String name);
}
