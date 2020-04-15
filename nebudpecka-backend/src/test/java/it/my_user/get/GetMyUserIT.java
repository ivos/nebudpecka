package it.my_user.get;

import it.support.RestClient;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.Setup;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LightAir.class)
@Setup
public class GetMyUserIT {

	private static final String PATH = "/api/my-user";

	private void ok(String response) {
		RestClient.from(this)
				.token("eca48917-9a92-4e5b-826e-17ff9d6c8c0f")
				.get(PATH)
				.responseName(response)
				.statusCode(HttpStatus.SC_OK)
				.eTag(1389225502L);
	}

	@Test
	public void full() {
		ok("full");
	}

	private void unauthorized(String token) {
		RestClient.from(this)
				.token(token)
				.get(PATH)
				.emptyResponse()
				.statusCode(HttpStatus.SC_UNAUTHORIZED)
				.header("WWW-Authenticate", "Basic realm=\"Backend\"");
	}

	@Test
	public void notFound() {
		unauthorized("eca48917-9a92-4e5b-826e-666666666666");
	}

	@Test
	public void expired() {
		unauthorized("eca48917-9a92-4e5b-826e-e8918ed00000");
	}
}
