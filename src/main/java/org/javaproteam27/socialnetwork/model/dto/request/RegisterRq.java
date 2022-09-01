package org.javaproteam27.socialnetwork.model.dto.request;

import lombok.Data;

@Data
public class RegisterRq {

    private String email;
    private String passwd1;
    private String passwd2;
    private String firstName;
    private String lastName;
    private String code;
    private String codeSecret;

}
