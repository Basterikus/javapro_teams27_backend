package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.entity.Person;

import java.util.List;

@Getter
@Setter
public class ListResponseDto {
    
    private String error;
    private long timestamp;
    private int total;
    private int offset;
    private int perPage;
    private List<PersonDto> data;
    
    public ListResponseDto(String error, int offset, int perPage, List<PersonDto> data) {
        this.error = error;
        this.timestamp = System.currentTimeMillis() / 1000;
        this.total = data.size();
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }
    
}
