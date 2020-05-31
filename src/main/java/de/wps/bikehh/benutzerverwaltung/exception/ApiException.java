package de.wps.bikehh.benutzerverwaltung.exception;

public class ApiException {
    //private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;


    public ApiException(String errorCode) {
        //this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private String errorMessage(String errorCode) {
        switch (errorCode) {
            case ErrorCode.bad_request:
                return "bad request made";
            case ErrorCode.unauthorized:
                return "is unauthorized";
            case ErrorCode.method_not_found:
                return "Http method not found";
        }

        return "unknown error code";
    }

    /*public HttpStatus getHttpStatus() {
        return httpStatus;
    }*/

}
