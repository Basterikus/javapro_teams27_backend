package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Data;

@Data
public class PostAuthLoginResponseDto {
    private String error;
    private Long timestamp;
    private PersonDto data;

    public void setExampleToLogin(String token){
        error = "string";
        timestamp = 1559751301818L;
        data = new PersonDto();
        data.setExampleToLogin(token);
    }
}
