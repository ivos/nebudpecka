package cz.lastware.nebudpecka.user;

import cz.lastware.nebudpecka.validation.DataIntegrityExceptionHandler;
import cz.lastware.nebudpecka.validation.ValidationError;
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
