package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListResponseDto<T> {
    
    private String error;
    private long timestamp;
    private int total;
    private int offset;
    private int perPage;
    private List<T> data;
    
    public ListResponseDto(String error, int offset, int perPage, List<T> data) {
        this.error = error;
        this.timestamp = System.currentTimeMillis();
        this.total = data.size();
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }
    
}
