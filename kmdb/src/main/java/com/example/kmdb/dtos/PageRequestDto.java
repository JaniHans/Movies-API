package com.example.kmdb.dtos;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

public class PageRequestDto {

    public Integer getPageNo() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNumber = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private Integer pageNumber = 0;
    private Integer pageSize = 10;

    /**
     * @param dto - page number and size through ternary not null object check
     * @return pageable object
     */
    public Pageable getPageable(PageRequestDto dto) {
       Integer page = Objects.nonNull(dto.getPageNo()) ? dto.pageNumber : this.pageNumber;
       Integer size = Objects.nonNull(dto.getPageSize()) ? dto.pageSize : this.pageSize;
      return PageRequest.of(page, size);
    }

}
