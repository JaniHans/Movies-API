package com.example.kmdb.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
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
     * @return pageable object
     */
    public Pageable getPageable(PageDto pageDto) {
        Integer page = Objects.nonNull(pageDto.getPageNumber()) ? getPageNumber() : this.pageNumber;
        Integer size = Objects.nonNull(pageDto.getPageNumber()) ? getPageSize() : this.pageSize;
    }
}
