package cz.lastware.pivospolu.utils;

import org.springframework.http.HttpHeaders;

import java.net.URI;

public class RestUtils {

	public static HttpHeaders eTag(Long version) {
		HttpHeaders headers = new HttpHeaders();
		headers.setETag("\"" + version + "\"");
		return headers;
	}

	public static Long version(String ifMatch) {
		if (ifMatch == null) {
			return -1L;
		}
		String stripped = ifMatch.replace("\"", ""); // remove wrapping double-quotes
		stripped = stripped.split("-")[0]; // remove trailing encodings, if any
		if ("null".equals(stripped)) {
			return -1L;
		}
		return Long.valueOf(stripped);
	}

	public static URI location(Long id) {
		return URI.create(String.valueOf(id));
	}
}
