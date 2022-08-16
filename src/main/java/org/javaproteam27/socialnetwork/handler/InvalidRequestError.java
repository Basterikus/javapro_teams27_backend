package org.javaproteam27.socialnetwork.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Data
public class InvalidRequestError {
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
