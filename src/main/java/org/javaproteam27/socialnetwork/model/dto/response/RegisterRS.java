package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class RegisterRS {

    private String error;

    @JsonInclude(NON_NULL)
    private long timestamp;

    @JsonInclude(NON_NULL)
    private HashMap<String, String> data;

    @JsonInclude(NON_NULL)
    @JsonProperty("error_description")
    private String errorDescription;
}
