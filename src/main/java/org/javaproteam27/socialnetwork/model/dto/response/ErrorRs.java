package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.ErrorDescription;
import org.javaproteam27.socialnetwork.model.enums.ErrorType;

@Data
@Builder
public class ErrorRs {
    
    private ErrorType error;
    @JsonProperty("error_description")
    private ErrorDescription errorDescription;
    
}
