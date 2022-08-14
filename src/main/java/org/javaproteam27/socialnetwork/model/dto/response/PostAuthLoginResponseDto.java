package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Data;
import org.javaproteam27.socialnetwork.model.entity.Person;

@Data
public class PostAuthLoginResponseDto {
    private String error;
    private Long timestamp;
    private PersonDto data;
}
