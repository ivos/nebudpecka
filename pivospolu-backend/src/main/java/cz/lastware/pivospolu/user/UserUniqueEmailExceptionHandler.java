package cz.lastware.pivospolu.user;

import cz.lastware.pivospolu.validation.DataIntegrityExceptionHandler;
import cz.lastware.pivospolu.validation.ValidationError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class UserUniqueEmailExceptionHandler implements DataIntegrityExceptionHandler {

	@Override
	public ValidationError handle(DataIntegrityViolationException exception) {
		if (exception.getMostSpecificCause().getMessage().contains("\"uc_users_email\"")) {
			return new ValidationError("email", null, "duplicate");
		}
		return null;
	}
}
