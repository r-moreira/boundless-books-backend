package com.extension.project.boundlessbooks.exception;

import com.extension.project.boundlessbooks.model.dto.HttpErrorBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<HttpErrorBody> handleBookNotFoundException(NotFoundException ex) {
        log.error("Resource not found", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new HttpErrorBody(
                        null,
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpErrorBody> handleGeneralException(Exception ex) {
        log.error("Internal server error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpErrorBody(
                        null,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }

    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<HttpErrorBody> handleNotImplementedException(NotImplementedException ex) {
        log.error("Not implemented", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(new HttpErrorBody(
                        ex.getMessage(),
                        HttpStatus.NOT_IMPLEMENTED.getReasonPhrase(),
                        HttpStatus.NOT_IMPLEMENTED.value()
                ));
    }
}