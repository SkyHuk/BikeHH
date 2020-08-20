package de.wps.bikehh.benutzerverwaltung.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.ConstraintViolationException;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
@RequestMapping(produces = "application/json")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ApiRequestException.class })
	public ResponseEntity<Object> handleApiBadRequestException(ApiRequestException e) {

		ApiException apiException = new ApiException(e.getMessage());

		return new ResponseEntity<>(apiException, e.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiException apiException = new ApiException(ErrorCode.unauthorized);
		return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiException apiException = new ApiException(ErrorCode.method_not_found);
		return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiException apiException = new ApiException(ErrorCode.bad_request);
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiException apiException = new ApiException(ErrorCode.bad_request);
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@Override // content-Type
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiException apiException = new ApiException(ErrorCode.bad_request);
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiException apiException = new ApiException(ErrorCode.bad_request);
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		System.err.println(ex.getMessage());
		ApiException apiException = new ApiException(ErrorCode.internal_server_error);
		return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(DataIntegrityViolationException ex) {
		System.err.println(ex.getMessage());
		ApiException apiException = new ApiException(ErrorCode.internal_server_error);
		return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(SqlExceptionHelper ex) {
		System.err.println(ex.toString());
		ApiException apiException = new ApiException(ErrorCode.internal_server_error);
		return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> unknownException(Exception ex, WebRequest req) {
		System.err.println(ex.getMessage());
		ApiException apiException = new ApiException(ErrorCode.internal_server_error);
		return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
