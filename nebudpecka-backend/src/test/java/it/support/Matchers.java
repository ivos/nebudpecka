package it.support;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.Objects;
import java.util.function.UnaryOperator;

import static org.junit.Assert.assertEquals;

public class Matchers {

	/**
	 * Asserts that values are equal JSON Strings.
	 *
	 * @param templateFilename template filename
	 * @param expected         expected JSON value
	 * @return matcher
	 */
	public static org.hamcrest.Matcher<String> jsonEqualTo(String templateFilename, String expected) {
		return new IsJsonEqual<>(templateFilename, expected, null);
	}

	/**
	 * Asserts that values are equal JSON Strings while applying a replacing function on the JSON model.
	 *
	 * @param templateFilename template filename
	 * @param expected         expected JSON value
	 * @param replacer         replacing function
	 * @return matcher
	 */
	public static org.hamcrest.Matcher<String> jsonEqualTo(
			String templateFilename, String expected, UnaryOperator<DocumentContext> replacer) {
		return new IsJsonEqual<>(templateFilename, expected, replacer);
	}

	public static class IsJsonEqual<T> extends BaseMatcher<String> {
		private final String templateFilename;
		private final String expected;
		private final UnaryOperator<DocumentContext> replacer;

		public IsJsonEqual(String templateFilename, String expected, UnaryOperator<DocumentContext> replacer) {
			this.templateFilename = templateFilename;
			this.expected = expected;
			this.replacer = replacer;
		}

		private String normalize(String content) {
			String json = JsonFormatter.format(content);
			if (json != null && !"null".equals(json)) {
				// pre-format first to ensure pretty error reporting of invalid JSON
				if (null != replacer) {
					json = replacer.apply(JsonPath.parse(json)).jsonString();
					json = JsonFormatter.format(json); // re-format again after replacing
				}
				// strip trailing zeroes from effectively whole numbers
				json = json.replaceAll("\\.0{1,3}(,\\r?\\n)", "$1");
			}
			return json;
		}

		@Override
		public boolean matches(Object actual) {
			String expectedNormalized = normalize(expected);
			String actualNormalized = normalize((String) actual);
			boolean match = Objects.equals(expectedNormalized, actualNormalized);
			if (!match) {
				assertEquals("Template file: (" + templateFilename + ":1)",
						expectedNormalized, actualNormalized);
			}
			return match;
		}

		@Override
		public void describeTo(Description description) {
			description.appendValue(expected);
		}
	}
}
