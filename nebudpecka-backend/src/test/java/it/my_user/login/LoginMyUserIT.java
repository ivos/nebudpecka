package it.my_user.login;

import it.support.RestClient;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.matchesPattern;

@RunWith(LightAir.class)
@Setup
@Verify
public class LoginMyUserIT {

	private static final String PATH = "/api/sessions";

	private void ok(String request) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.emptyResponse()
				.statusCode(HttpStatus.SC_CREATED)
				.location(matchesPattern("^[0-9a-f\\-]{36}$"));
	}

	@Test
	public void full() {
		ok("full");
	}

	private void error(String request, int status) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.responseName(request, ctx -> ctx.set("$.timestamp", "REPLACED"))
				.statusCode(status);
	}

	private void validation(String request) {
		error(request, HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}

	@Test
	@Verify("LoginMyUserIT.xml")
	public void empty() {
		validation("empty");
	}

	@Test
	@Verify("LoginMyUserIT.xml")
	public void notFound() {
		error("notFound", HttpStatus.SC_NOT_FOUND);
	}

	@Test
	@Verify("LoginMyUserIT.xml")
	public void invalidPassword() {
		error("invalidPassword", HttpStatus.SC_UNAUTHORIZED);
	}
}
