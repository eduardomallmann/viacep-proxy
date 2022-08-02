package com.eduardomallmann.studies.example.exceptions;

import com.eduardomallmann.studies.example.utils.PropertiesConstants;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler, responsible to catch Location exceptions launched from the controller and treat as per application convenience.
 *
 * @author eduardomallmann
 */
@Slf4j
@ControllerAdvice
public class LocationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles business logic exception response to the origin.
     *
     * @param ex exception thrown
     *
     * @return the exceptions in a error message standard inside a response entity.
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ObjectError> handleBusinessExceptions(final BusinessException ex) {
        log.error("M=handleBusinessExceptions, message=Start handling BusinessException, error={}", ex.getObjectError());
        return ResponseEntity.badRequest().body(ex.getObjectError());
    }

    /**
     * Handles constraint violation exceptions coming from validation.
     *
     * @param e constrain violation validation error
     *
     * @return the exceptions in a error message standard inside a response entity.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ObjectError> handleConstraintViolationException(final ConstraintViolationException e,
                                                                             final WebRequest request) {
        log.error("M=handleConstraintViolationException, violations={}", e.getConstraintViolations());
        ObjectError error = new ObjectError(PropertiesConstants.WRONG_CEP_NUMBER.getErrorKey(), e.getMessage());
        if (MediaType.APPLICATION_XML_VALUE.equals(request.getHeader("Accept"))) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_XML).body(error);
        }
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ObjectError> handleGlobalException(final Exception e) {
        log.error("M=handleGlobalException, violations={}", e.getMessage());
        ObjectError error = new ObjectError(PropertiesConstants.WRONG_CEP_NUMBER.getErrorKey(), e.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
    }
}
