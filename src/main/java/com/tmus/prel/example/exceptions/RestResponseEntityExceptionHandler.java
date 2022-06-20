package com.tmus.prel.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class responsible for filtering the errors and response them in a global standard to the origin.
 *
 * @author eduardomallmann
 * @since 0.0.1
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles object search not found exception conflict response to the origin.
     *
     * @param ex exception thrown
     *
     * @return the exceptions in a error message standard inside a response entity.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ObjectError> handleBusinessExceptions(final BusinessException ex) {
        log.error("M=handleBusinessExceptions, message=Start handling BusinessException, error={}",ex.getObjectError());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(ex.getObjectError());
    }

    /**
     * Handles the global exceptions.
     *
     * @param ex exception thrown
     *
     * @return the exceptions in a error message standard inside a response entity.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ObjectError> handleExceptions(final Exception ex) {
        ObjectError error = ObjectError.builder().exception(ex.getClass().getName()).message(ex.getMessage()).build();
        log.error("M=handleExceptions, message=Starting standard exception handler, errorClass={}, errorMessage={}", ex.getClass(), error);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
    }

    /**
     * Transforms the validation exceptions into an application exception to be handle by the consumer.
     *
     * @param ex      Exception thrown
     * @param headers response headers
     * @param status  response status
     * @param request http request
     *
     * @return the exceptions in an error message standard inside a response entity.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ObjectError error = ObjectError.builder().exception(ex.getClass().getName()).message(ex.getMessage()).build();
        log.error("M=handleMethodArgumentNotValid, message=Starting handle method argument not valid exception, errorClass={}, errorMessage={}", ex.getClass(), error);
        return ResponseEntity.status(status).headers(headers).contentType(MediaType.APPLICATION_JSON).body(error);
    }
}
