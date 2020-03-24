package it.user.create;

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
public class CreateUserIT {

	private static final String PATH = "/api/users";

	private void ok(String request) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.emptyResponse()
				.statusCode(HttpStatus.SC_CREATED)
				.location(matchesPattern("^[0-9]+$"));
	}

	@Test
	public void full() {
		ok("full");
	}

	@Test
	public void minimal() {
		ok("minimal");
	}

	private void validation(String request) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.responseName(request, ctx -> ctx.set("$.timestamp", "REPLACED"))
				.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}

	@Test
	@Verify("CreateUserIT.xml")
	public void empty() {
		validation("empty");
	}

	@Test
	@Verify("CreateUserIT.xml")
	public void emptyValues() {
		validation("emptyValues");
	}

	@Test
	@Verify("CreateUserIT.xml")
	public void longValues() {
		validation("longValues");
	}

	@Test
	@Verify("CreateUserIT.xml")
	public void duplicateEmail() {
		validation("duplicateEmail");
	}
}
