package org.javaproteam27.socialnetwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeedsDto {
    private String name;
    private Integer offset;
    private Integer itemPerPage;

    public FeedsDto(@JsonProperty("name") String name
            , @JsonProperty("offset") Integer offset
            , @JsonProperty("itemPerPage") Integer itemPerPage) {
        this.name = name;
        this.offset = offset;
        this.itemPerPage = itemPerPage;
    }
}
