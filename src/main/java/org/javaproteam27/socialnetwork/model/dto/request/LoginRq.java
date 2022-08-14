package org.javaproteam27.socialnetwork.model.dto.request;

import lombok.Value;

@Value
public class LoginRq {
    String email;
    String password;
}
