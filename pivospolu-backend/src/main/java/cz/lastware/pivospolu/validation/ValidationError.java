package cz.lastware.pivospolu.validation;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ValidationError implements Comparable<ValidationError> {

	private final String path;
	private final Object value;
	private final String errorCode;

	@Override
	public int compareTo(ValidationError o) {
		int byPath = path.compareTo(o.path);
		int byErrorCode = errorCode.compareTo(o.errorCode);
		return (0 == byPath) ? byErrorCode : byPath;
	}
}
