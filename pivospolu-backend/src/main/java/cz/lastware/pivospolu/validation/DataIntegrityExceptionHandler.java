package cz.lastware.pivospolu.validation;

import org.springframework.dao.DataIntegrityViolationException;

public interface DataIntegrityExceptionHandler {

	ValidationError handle(DataIntegrityViolationException exception);
}
