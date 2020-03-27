package cz.lastware.nebudpecka.validation;

import org.springframework.dao.DataIntegrityViolationException;

public interface DataIntegrityExceptionHandler {

	ValidationError handle(DataIntegrityViolationException exception);
}
