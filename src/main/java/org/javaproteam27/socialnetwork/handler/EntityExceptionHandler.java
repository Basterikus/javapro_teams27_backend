package org.javaproteam27.socialnetwork.handler;

import lombok.extern.slf4j.Slf4j;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.handler.exception.InvalidRequestException;
import org.javaproteam27.socialnetwork.handler.exception.UnableCreateEntityException;
import org.javaproteam27.socialnetwork.model.dto.response.ErrorRs;
import org.javaproteam27.socialnetwork.model.enums.ErrorDescription;
import org.javaproteam27.socialnetwork.model.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class EntityExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ErrorRs> catchInvalidRequestException(InvalidRequestException e) {
        log.error(e.getMessage(), e);
        ErrorRs errorRs = ErrorRs.builder()
                .error(ErrorType.INVALID_REQUEST)
                .errorDescription(ErrorDescription.INVALID_REQUEST)
                .build();
        return new ResponseEntity<>(errorRs, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public ResponseEntity<ErrorRs> catchEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        ErrorRs errorRs = ErrorRs.builder()
                .error(ErrorType.INVALID_REQUEST)
                .errorDescription(ErrorDescription.INVALID_REQUEST)
                .build();
        return new ResponseEntity<>(errorRs, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public ResponseEntity<ErrorRs> catchUnableCreateEntityException(UnableCreateEntityException e) {
        log.error(e.getMessage(), e);
        ErrorRs errorRs = ErrorRs.builder()
                .error(ErrorType.INVALID_REQUEST)
                .errorDescription(ErrorDescription.INVALID_REQUEST)
                .build();
        return new ResponseEntity<>(errorRs, HttpStatus.BAD_REQUEST);
    }
}
