package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class ListResponseDto<T> {
    private String error;
    private Long timestamp;
    private Integer total;
    private Integer offset;
    private Integer perPage;
    private List<T> data;
}