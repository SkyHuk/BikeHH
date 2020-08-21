package de.wps.bikehh.framework.api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiException {

	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ApiException(HttpStatus status) {
		this(status, status.getReasonPhrase());
	}

	public ApiException(HttpStatus status, String message) {
		this(status, message, null);
	}

	public ApiException(HttpStatus status, String message, List<String> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}

}
