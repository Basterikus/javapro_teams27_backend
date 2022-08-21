package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PostResponseDtoRs {
    String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    PostDtoRs postDtoRs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("error_description")
    String errorDescription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    LocalDateTime timestamp;
}
