package org.javaproteam27.socialnetwork.handler;

import lombok.extern.slf4j.Slf4j;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class LoginExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<InvalidRequestError> catchInvalidRequestException(InvalidRequestException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new InvalidRequestError("invalid_request", e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<InvalidRequestError> catchEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new InvalidRequestError("invalid_request", "Incorrect email"),
                HttpStatus.BAD_REQUEST);
    }
}
