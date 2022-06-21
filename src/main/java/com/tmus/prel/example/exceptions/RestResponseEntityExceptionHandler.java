package com.tmus.prel.example.exceptions;

import com.tmus.prel.example.utils.PropertiesConstants;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
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

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ObjectError> handleBusinessExceptions(final BusinessException ex) {
        log.error("M=handleBusinessExceptions, message=Start handling BusinessException, error={}",ex.getObjectError());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(ex.getObjectError());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ObjectError> handleConstraintViolationException(final ConstraintViolationException e) {
        log.error("M=handleConstraintViolationException, violations={}", e.getConstraintViolations());
        ObjectError error = new ObjectError(PropertiesConstants.WRONG_CEP_NUMBER.getErrorKey(), e.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ObjectError> handleExceptions(final Exception ex) {
        ObjectError error = ObjectError.builder().exception(ex.getClass().getName()).message(ex.getMessage()).build();
        log.error("M=handleExceptions, message=Starting standard exception handler, errorClass={}, errorMessage={}", ex.getClass(), error);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(error);
    }

}
