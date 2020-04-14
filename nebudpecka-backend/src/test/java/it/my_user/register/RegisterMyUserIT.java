package it.my_user.register;

import it.support.RestClient;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LightAir.class)
@Setup
@Verify
public class RegisterMyUserIT {

	private static final String PATH = "/api/register";

	private void ok(String request) {
		RestClient.from(this)
				.requestName(request)
				.post(PATH)
				.emptyResponse()
				.statusCode(HttpStatus.SC_OK);
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
	@Verify("RegisterMyUserIT.xml")
	public void empty() {
		validation("empty");
	}

	@Test
	@Verify("RegisterMyUserIT.xml")
	public void emptyValues() {
		validation("emptyValues");
	}

	@Test
	@Verify("RegisterMyUserIT.xml")
	public void longValues() {
		validation("longValues");
	}

	@Test
	@Verify("RegisterMyUserIT.xml")
	public void duplicateEmail() {
		validation("duplicateEmail");
	}
}
