package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListResponseDtoRs<T> {

    private String error;
    private long timestamp;
    private int total;
    private int offset;
    private int perPage;
    private List<T> data;

    public ListResponseDtoRs(String error, int offset, int perPage, List<T> data) {
        this.error = error;
        this.timestamp = System.currentTimeMillis();//LocalDateTime.now()
        this.total = data.size();
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }



}
