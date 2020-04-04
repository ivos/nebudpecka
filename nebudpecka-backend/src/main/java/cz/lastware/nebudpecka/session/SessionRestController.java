package cz.lastware.nebudpecka.session;

import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoLogin;
import cz.lastware.nebudpecka.utils.RestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
@Logged(Logged.LogLevel.info)
public class SessionRestController {

	private final SessionService sessionService;

	public SessionRestController(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> login(
			@RequestBody MyUserDtoLogin dto) {
		Session session = sessionService.login(dto);
		return ResponseEntity
				.created(RestUtils.location(session.getToken()))
				.build();
	}
}
