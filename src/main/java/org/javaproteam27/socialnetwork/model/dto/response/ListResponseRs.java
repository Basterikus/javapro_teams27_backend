package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListResponseRs<T> {

    private String error;
    private Long timestamp;
    private Integer total;
    private Integer offset;
    private Integer perPage;
    private List<T> data;
    @JsonProperty("error_description")
    private String errorDescription;

    public ListResponseRs(String error, int offset, int perPage, List<T> data) {
        this.error = error;
        this.timestamp = System.currentTimeMillis();
        this.total = data.size();
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }

}
