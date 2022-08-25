package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Builder
public class ResponseDtoRs<T> {
    private String error;
    private long timestamp;
    private int offset;
    private int perPage;
    private T data;
    @JsonProperty("error_description")
    String errorDescription;

    public ResponseDtoRs(String error, int offset, int perPage, T data) {
        this.error = error;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseDtoRs(String error, T data, String errorDescription){
        this.error = error;
        this.data = data;
        this.errorDescription = errorDescription;
        this.timestamp = System.currentTimeMillis();
    }
}
