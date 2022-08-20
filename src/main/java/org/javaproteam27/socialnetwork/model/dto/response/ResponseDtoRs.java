package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ResponseDtoRs<T> {
    private String error;
    private LocalDateTime timestamp;
    private int offset;
    private int perPage;
    private T data;

    public ResponseDtoRs(String error, int offset, int perPage, T data) {
        this.error = error;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
