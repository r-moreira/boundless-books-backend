package com.extension.project.boundlessbooks.exception;

import com.extension.project.boundlessbooks.model.dto.HttpErrorBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
            schema = @Schema(implementation = HttpErrorBody.class),
            examples = @ExampleObject(value = """
                            {
                                "message": "Invalid input provided",
                                "error": "Bad Request",
                                "status": 400
                            }
                            """)
    ))
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,
            BadRequestException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpErrorBody> handleBadRequest(Exception ex) {
        log.error("Bad Request", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new HttpErrorBody(
                        "Invalid input provided",
                        "Bad Request",
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
            schema = @Schema(implementation = HttpErrorBody.class),
            examples = @ExampleObject(value = """
                            {
                                "message": "Unauthorized access",
                                "error": "Forbidden",
                                "status": 401
                            }
                            """)
    ))
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<HttpErrorBody> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("Unauthorized", ex);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new HttpErrorBody(
                        "Unauthorized access",
                        "Forbidden",
                        HttpStatus.UNAUTHORIZED.value()
                ));
    }

    @ApiResponse(
            responseCode = "404", description = "Not Found", content = @Content(
                    schema = @Schema(implementation = HttpErrorBody.class),
                    examples = @ExampleObject(value = """
                            {
                                "message": "Resource not found",
                                "error": "Not Found",
                                "status": 404
                            }
                            """)
    ))
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<HttpErrorBody> handleNotFoundException(NotFoundException ex) {
        log.error("Resource not found", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new HttpErrorBody(
                        "Resource not found",
                        "Not Found",
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ApiResponse(
            responseCode = "500", description = "Internal Server Error", content = @Content(
                    schema = @Schema(implementation = HttpErrorBody.class),
                    examples = @ExampleObject(value = """
                            {
                                "message": "An unexpected error occurred",
                                "error": "Internal Server Error",
                                "status": 500
                            }
                            """)
    ))
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpErrorBody> handleGeneralException(Exception ex) {
        log.error("Internal server error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HttpErrorBody(
                        "An unexpected error occurred",
                        "Internal Server Error",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }

    @ApiResponse(
            responseCode = "501", description = "Not Implemented", content = @Content(
                    schema = @Schema(implementation = HttpErrorBody.class),
                    examples = @ExampleObject(value = """
                            {
                                "message": "Feature not implemented",
                                "error": "Not Implemented",
                                "status": 501
                            }
                            """)
    ))
    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<HttpErrorBody> handleNotImplementedException(NotImplementedException ex) {
        log.error("Not implemented", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(new HttpErrorBody(
                        "Feature not implemented",
                        "Not Implemented",
                        HttpStatus.NOT_IMPLEMENTED.value()
                ));
    }
}