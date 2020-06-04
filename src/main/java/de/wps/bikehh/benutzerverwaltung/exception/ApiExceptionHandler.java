package de.wps.bikehh.benutzerverwaltung.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiBadRequestException(ApiRequestException e) {

        ApiException apiException = new ApiException(e.getMessage());

        return new ResponseEntity<>(apiException, e.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiException headerException = new ApiException(ErrorCode.unauthorized);
        return new ResponseEntity<>(headerException, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiException headerException = new ApiException(ErrorCode.method_not_found);
        return new ResponseEntity<>(headerException, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiException headerException = new ApiException(ErrorCode.bad_request);
        return new ResponseEntity<>(headerException, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiException headerException = new ApiException(ErrorCode.bad_request);
        return new ResponseEntity<>(headerException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {

        ApiException apiException = new ApiException(e.getMessage());
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @Override //content-Type
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiException headerException = new ApiException(ErrorCode.bad_request);
        return new ResponseEntity<>(headerException, HttpStatus.BAD_REQUEST);
    }

}
