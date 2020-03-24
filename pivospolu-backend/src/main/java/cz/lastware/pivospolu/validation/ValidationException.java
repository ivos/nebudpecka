package cz.lastware.pivospolu.validation;

import java.util.List;

/**
 * Validation exception.
 * <p>
 * Contains a list of {@link ValidationError}s.
 */
public class ValidationException extends RuntimeException {
	private final List<ValidationError> validationErrors;

	public ValidationException(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	@Override
	public String getMessage() {
		return validationErrors.toString();
	}
}
