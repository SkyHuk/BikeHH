package de.wps.bikehh.benutzerverwaltung.exception;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException{

    private HttpStatus httpStatus;

    public ApiRequestException(String errorCode, HttpStatus httpStatus) {
        super(errorCode);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
