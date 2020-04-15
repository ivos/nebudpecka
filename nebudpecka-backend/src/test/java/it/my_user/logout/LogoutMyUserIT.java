package it.my_user.logout;

import it.support.RestClient;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import static it.support.RestClient.TIMESTAMP_REPLACER;

@RunWith(LightAir.class)
@Setup
@Verify
public class LogoutMyUserIT {

	private static final String PATH = "/api/logout";

	private void ok(String token) {
		RestClient.from(this)
				.token(token)
				.put(PATH)
				.emptyResponse()
				.statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void full() {
		ok("eca48917-9a92-4e5b-826e-17ff9d6c8c0f");
	}

	private RestClient.ResponseBuilder unauthorized(String token) {
		return RestClient.from(this)
				.token(token)
				.put(PATH)
				.statusCode(HttpStatus.SC_UNAUTHORIZED)
				.header("WWW-Authenticate", "Basic realm=\"Backend\"");
	}

	@Test
	@Verify("LogoutMyUserIT.xml")
	public void notFound() {
		unauthorized("eca48917-9a92-4e5b-826e-666666666666")
				.emptyResponse();
	}

	@Test
	@Verify("LogoutMyUserIT.xml")
	public void expired() {
		unauthorized("eca48917-9a92-4e5b-826e-e8918ed00000")
				.emptyResponse();
	}

	@Test
	@Verify("LogoutMyUserIT.xml")
	public void noToken() {
		unauthorized(null)
				.responseName("unauthorized", TIMESTAMP_REPLACER);
	}
}
