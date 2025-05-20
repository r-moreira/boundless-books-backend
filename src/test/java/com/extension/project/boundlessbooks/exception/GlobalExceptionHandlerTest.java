package com.extension.project.boundlessbooks.exception;

import com.extension.project.boundlessbooks.model.dto.HttpErrorBody;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBadRequest_ReturnsBadRequestResponse() {
        Exception exception = new BadRequestException("Invalid input");
        ResponseEntity<HttpErrorBody> response = handler.handleBadRequest(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid input provided", response.getBody().message());
        assertEquals("Bad Request", response.getBody().error());
        assertEquals(400, response.getBody().status());
    }

    @Test
    void handleUnauthorizedException_ReturnsUnauthorizedResponse() {
        UnauthorizedException exception = new UnauthorizedException("Unauthorized access");
        ResponseEntity<HttpErrorBody> response = handler.handleUnauthorizedException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unauthorized access", response.getBody().message());
        assertEquals("Forbidden", response.getBody().error());
        assertEquals(401, response.getBody().status());
    }

    @Test
    void handleNotFoundException_ReturnsNotFoundResponse() {
        NotFoundException exception = new NotFoundException("Resource not found");
        ResponseEntity<HttpErrorBody> response = handler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Resource not found", response.getBody().message());
        assertEquals("Not Found", response.getBody().error());
        assertEquals(404, response.getBody().status());
    }

    @Test
    void handleGeneralException_ReturnsInternalServerErrorResponse() {
        Exception exception = new Exception("Unexpected error");
        ResponseEntity<HttpErrorBody> response = handler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("An unexpected error occurred", response.getBody().message());
        assertEquals("Internal Server Error", response.getBody().error());
        assertEquals(500, response.getBody().status());
    }

    @Test
    void handleNotImplementedException_ReturnsNotImplementedResponse() {
        NotImplementedException exception = new NotImplementedException("Feature not implemented");
        ResponseEntity<HttpErrorBody> response = handler.handleNotImplementedException(exception);

        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Feature not implemented", response.getBody().message());
        assertEquals("Not Implemented", response.getBody().error());
        assertEquals(501, response.getBody().status());
    }
}