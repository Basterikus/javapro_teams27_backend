package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;


@Value
@JsonPropertyOrder({"error", "timestamp", "data", "errorDescription"})
public class PostResponseDtoRs {
    String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    PostDtoRs postDtoRs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("error_description")
    String errorDescription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long timestamp;
}
