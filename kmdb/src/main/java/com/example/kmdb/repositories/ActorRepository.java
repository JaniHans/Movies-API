package com.example.kmdb.repositories;

import com.example.kmdb.entities.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<ActorEntity, Integer> {
}
