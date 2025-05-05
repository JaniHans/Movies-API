package com.example.kmdb.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@Data
public class PageDto {

    // automatically provides a default no-argument constructor
    @NotNull
    private Integer pageNumber = 0;
    @NotNull
    private Integer pageSize = 10;

    /**
     * @param pageDto - page number and size through ternary not null object check
     * Pageable is an interface built in spring
     * @return pageable object
     */
    public Pageable getPageable() {
        Integer page = Objects.nonNull(getPageNumber()) ? getPageNumber() : this.pageNumber;
        Integer size = Objects.nonNull(getPageNumber()) ? getPageSize() : this.pageSize;

        PageRequest request = PageRequest.of(page, size);

        return request; // returning a PageRequest as a Pageable
    }
}
