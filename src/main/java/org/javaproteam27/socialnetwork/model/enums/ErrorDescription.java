package org.javaproteam27.socialnetwork.model.enums;

import lombok.Getter;

@Getter
public enum ErrorDescription {
    
    INVALID_REQUEST("invalid_request"),
    UNAUTHORIZED("unauthorized");
    //todo : add error codes
    
    private final String text;
    
    ErrorDescription(String text) {
        this.text = text;
    }
    
}
