package com.example.kmdb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface PagingAndSortingRepository <T, ID> extends CrudRepository {

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);
}
