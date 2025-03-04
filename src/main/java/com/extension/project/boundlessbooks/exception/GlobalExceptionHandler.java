package com.extension.project.boundlessbooks.exception;

import com.extension.project.boundlessbooks.model.dto.HttpErrorBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpErrorBody> handleBadRequest(Exception ex) {
        log.error("Bad Request", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new HttpErrorBody(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<HttpErrorBody> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("Unauthorized", ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new HttpErrorBody(
                        ex.getMessage(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        HttpStatus.FORBIDDEN.value()
                ));
    }

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