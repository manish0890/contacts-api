package com.manish0890.contacts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish0890.contacts.model.ErrorSummary;
import com.manish0890.contacts.model.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice // intercept exceptions from controllers across the application.
public class CustomExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) throws JsonProcessingException {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapper.writeValueAsString(buildErrorSummary(ex, HttpStatus.NOT_FOUND.value())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) throws JsonProcessingException {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(mapper.writeValueAsString(buildErrorSummary(ex, HttpStatus.PRECONDITION_FAILED.value())));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Object> handleSqlConstraintViolationException(DataIntegrityViolationException ex) throws JsonProcessingException {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(mapper.writeValueAsString(buildErrorSummary(ex, HttpStatus.PRECONDITION_FAILED.value())));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception ex) throws JsonProcessingException {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapper.writeValueAsString(buildErrorSummary(ex, HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    /**
     * Build the error summary object.
     *
     * @param ex     the {@link Exception}
     * @param status the {@link String} status
     * @return the {@link ErrorSummary}
     */
    private ErrorSummary buildErrorSummary(Exception ex, int status) {
        ErrorSummary errorSummary = new ErrorSummary();
        errorSummary.setTimestamp(LocalDateTime.now().toString());
        errorSummary.setMessage(ex != null ? ex.getMessage() : "NULL Exception");
        errorSummary.setStatus(status);
        return errorSummary;
    }
}
