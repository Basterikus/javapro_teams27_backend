package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListResponseDtoRs<T> {

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
//    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long timestamp;
//    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int total;
//    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int offset;
//    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
