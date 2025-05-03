package com.example.kmdb.dtos;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@Data
public class PageDto {



    // automatically provides a default no-argument constructor
    private Integer pageNumber = 0;
    private Integer pageSize = 10;

    /**
     * @param dto - page number and size through ternary not null object check
     * @return pageable object
     */
    public Pageable getPageable(PageDto dto) {
        Integer page = Objects.nonNull(dto.getPageNumber()) ? getPageNumber() : this.pageNumber;
        Integer size = Objects.nonNull(dto.getPageNumber()) ? getPageSize() : this.pageSize;
    }
}
