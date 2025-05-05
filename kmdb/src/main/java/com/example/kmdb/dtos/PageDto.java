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
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;

    /**
     * Pageable is an interface built in spring
     * @return pageable object
     */
    public Pageable getPageable() {
        Integer page = Objects.nonNull(this.pageNumber) ? this.pageNumber : 0;
        Integer size = Objects.nonNull(this.pageSize) ? this.pageSize : 10;

        return PageRequest.of(page, size);

    }
}
