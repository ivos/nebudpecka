package cz.lastware.nebudpecka.config;

import cz.lastware.nebudpecka.validation.DataIntegrityExceptionHandler;
import cz.lastware.nebudpecka.validation.ValidationError;
import cz.lastware.nebudpecka.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ControllerAdvice("cz.lastware.nebudpecka")
public class ExceptionHandler {

	private final List<DataIntegrityExceptionHandler> dataIntegrityExceptionHandlers;

	public ExceptionHandler(List<DataIntegrityExceptionHandler> dataIntegrityExceptionHandlers) {
		this.dataIntegrityExceptionHandlers = dataIntegrityExceptionHandlers;
	}

	private ErrorResponse createErrorResponse(HttpServletRequest request, HttpStatus status) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(new Date());
		errorResponse.setStatus(status.value());
		errorResponse.setError(status.getReasonPhrase());
		errorResponse.setPath(request.getRequestURI());
		return errorResponse;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handle(HttpServletRequest request, ValidationException exception) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ErrorResponse errorResponse = createErrorResponse(request, status);
		errorResponse.setErrors(exception.getValidationErrors());
		return new ResponseEntity<>(errorResponse, status);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handle(HttpServletRequest request, DataIntegrityViolationException exception) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ErrorResponse errorResponse = createErrorResponse(request, status);
		ValidationError validationError =
				dataIntegrityExceptionHandlers.stream()
						.map(handler -> handler.handle(exception))
						.filter(Objects::nonNull)
						.findFirst()
						.orElse(new ValidationError(null, null, exception.getMostSpecificCause().getMessage()));
		errorResponse.setErrors(Collections.singletonList(validationError));
		return new ResponseEntity<>(errorResponse, status);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handle(HttpServletRequest request, EntityNotFoundException exception) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponse errorResponse = createErrorResponse(request, status);
		errorResponse.setError("Not Found");
		return new ResponseEntity<>(errorResponse, status);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(OptimisticLockException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handle(HttpServletRequest request, RuntimeException exception) {
		HttpStatus status = HttpStatus.CONFLICT;
		ErrorResponse errorResponse = createErrorResponse(request, status);
		return new ResponseEntity<>(errorResponse, status);
	}
}
