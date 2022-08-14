package org.javaproteam27.socialnetwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public class LoginFormDto {
    private String email;
    private String password;

    public LoginFormDto(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
