package cz.lastware.pivospolu.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Validation {

	@Autowired
	private Validator validator;

	/**
	 * Perform bean validations (JSR-303).
	 * <p>
	 * Calls verify method to stop processing.
	 *
	 * @param data bean to be validated, annotated with JSR-303 annotations
	 */
	public void verifyBean(Object data) {
		List<ValidationError> validationErrors = validator.validate(data).stream()
				.map(violation -> new ValidationError(
						violation.getPropertyPath().toString(),
						violation.getInvalidValue(),
						violation.getMessage()))
				.sorted()
				.collect(Collectors.toList());
		verify(validationErrors);
	}

	/**
	 * Rejects further processing due to a validation error.
	 * <p>
	 * Calls verify method to stop processing.
	 *
	 * @param validationError validation error
	 */
	public void reject(ValidationError validationError) {
		verify(Collections.singletonList(validationError));
	}

	/**
	 * Verify that no errors have been collected during previous validations.
	 * <p>
	 * If there are any errors, throw {@link ValidationException}.
	 *
	 * @param validationErrors validation errors
	 */
	public void verify(List<ValidationError> validationErrors) {
		if (!validationErrors.isEmpty()) {
			throw new ValidationException(validationErrors);
		}
	}
}
