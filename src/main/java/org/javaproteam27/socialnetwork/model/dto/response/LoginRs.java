package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Value;

import java.util.Date;

@Value
public class LoginRs {
    String error;
    Date timestamp;
    LoginDataRs data;
}
