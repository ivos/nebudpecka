package it.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matcher;
import org.springframework.http.HttpHeaders;

import java.util.function.UnaryOperator;

import static it.support.Matchers.jsonEqualTo;
import static org.junit.Assert.assertEquals;

/**
 * Support class for invoking REST client requests with fluent API.
 */
public class RestClient {

	public static final String CONTENT_TYPE = "application/json";

	public static RequestBuilder from(Object testInstance) {
		return new RequestBuilder(testInstance);
	}

	public static class RequestBuilder {
		private final Object testInstance;
		private final RequestSpecification rqSpec;
		private Response resp;

		private RequestBuilder(Object testInstance) {
			this.testInstance = testInstance;
			rqSpec = RestAssured.given();
		}

		public RequestBuilder requestName(String testCase) {
			return this.requestName(testCase, null);
		}

		public RequestBuilder requestName(String testCase, UnaryOperator<DocumentContext> replacer) {
			String request = FileUtils.load(testInstance, testCase + "-request.json");
			if (replacer != null) {
				request = replacer.apply(JsonPath.parse(request)).jsonString();
			}
			rqSpec.body(request);
			rqSpec.contentType(CONTENT_TYPE);
			return this;
		}

		public RequestBuilder requestContent(String content) {
			rqSpec.body(content);
			rqSpec.contentType(CONTENT_TYPE);
			return this;
		}

		public RequestBuilder request(JsonNode node) {
			rqSpec.body(JsonFormatter.convertToString(node));
			rqSpec.contentType(CONTENT_TYPE);
			return this;
		}

		public RequestBuilder queryParam(String parameterName, Object... parameterValues) {
			if (null != parameterValues && parameterValues.length > 0) {
				rqSpec.queryParam(parameterName, parameterValues);
			}
			return this;
		}

		public RequestBuilder ifMatch(Long version) {
			rqSpec.header(HttpHeaders.IF_MATCH, "\"" + version + "-gzip\"");
			return this;
		}

		public ResponseBuilder post(String path) {
			resp = rqSpec.post(path);
			return new ResponseBuilder(this);
		}

		public ResponseBuilder put(String path) {
			resp = rqSpec.put(path);
			return new ResponseBuilder(this);
		}

		public ResponseBuilder delete(String path) {
			resp = rqSpec.delete(path);
			return new ResponseBuilder(this);
		}

		public ResponseBuilder get(String path) {
			resp = rqSpec.get(path);
			return new ResponseBuilder(this);
		}
	}

	public static class ResponseBuilder {
		private final RequestBuilder requestBuilder;
		private final Object testInstance;
		private ValidatableResponse valResp;

		private ResponseBuilder(RequestBuilder requestBuilder) {
			this.requestBuilder = requestBuilder;
			testInstance = requestBuilder.testInstance;
			valResp = requestBuilder.resp.then();
		}

		public ResponseBuilder statusCode(int status) {
			valResp.statusCode(status);
			return this;
		}

		public ResponseBuilder responseName(String testCase) {
			responseName(testCase, null);
			return this;
		}

		public ResponseBuilder responseName(String testCase, UnaryOperator<DocumentContext> replacer) {
			String fileName = testCase + "-response.json";
			String expectedResponse = FileUtils.load(testInstance, fileName);
			valResp.body(jsonEqualTo(fileName, expectedResponse, replacer));
			valResp.contentType(CONTENT_TYPE);
			return this;
		}

		public ResponseBuilder responsePath(String path, Matcher<?> matcher) {
			valResp.body(path, matcher);
			return this;
		}

		public String getResponseAsString() {
			return requestBuilder.resp.getBody().asString();
		}

		public byte[] getResponseAsByteArray() {
			return requestBuilder.resp.getBody().asByteArray();
		}

		public ResponseBuilder contentType(String contentType) {
			valResp.contentType(contentType);
			return this;
		}

		public ResponseBuilder emptyResponse() {
			assertEquals("", getResponseAsString());
			return this;
		}

		public ResponseBuilder header(String name, String value) {
			valResp.header(name, value);
			return this;
		}

		public ResponseBuilder eTag(Long version) {
			header(HttpHeaders.ETAG, "\"" + version + "\"");
			return this;
		}

		public ResponseBuilder location(Matcher<?> matcher) {
			valResp.header(HttpHeaders.LOCATION, matcher);
			return this;
		}
	}
}
