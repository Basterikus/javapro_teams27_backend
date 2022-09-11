package org.javaproteam27.socialnetwork.model.enums;

import lombok.Getter;

@Getter
public enum ErrorType {
    
    INVALID_REQUEST("invalid_request"),
    UNAUTHORIZED("unauthorized");
    
    private final String text;
    
    ErrorType(String text) {
        this.text = text;
    }

}
