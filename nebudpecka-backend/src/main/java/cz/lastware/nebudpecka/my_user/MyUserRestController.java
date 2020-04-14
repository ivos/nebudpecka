package cz.lastware.nebudpecka.my_user;

import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoGet;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoLogin;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoRegister;
import cz.lastware.nebudpecka.session.Session;
import cz.lastware.nebudpecka.session.SessionService;
import cz.lastware.nebudpecka.user.User;
import cz.lastware.nebudpecka.utils.RestUtils;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static cz.lastware.nebudpecka.utils.RestUtils.eTag;

@RestController
@RequestMapping("/api")
@Logged(Logged.LogLevel.info)
public class MyUserRestController {

	private final MyUserService myUserService;
	private final SessionService sessionService;
	private final MapperFacade mapper;

	public MyUserRestController(MyUserService myUserService, SessionService sessionService, MapperFacade mapper) {
		this.myUserService = myUserService;
		this.sessionService = sessionService;
		this.mapper = mapper;
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public void register(
			@RequestBody MyUserDtoRegister dto) {
		myUserService.register(dto);
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<Void> login(
			@RequestBody MyUserDtoLogin dto) {
		Session session = sessionService.login(dto);
		return ResponseEntity
				.created(RestUtils.location(session.getToken()))
				.build();
	}

	@RequestMapping(path = "/my-user", method = RequestMethod.GET)
	public ResponseEntity<MyUserDtoGet> getMyUser() {
		User user = myUserService.getMyUser();
		MyUserDtoGet dto = mapper.map(user, MyUserDtoGet.class);
		return ResponseEntity
				.ok()
				.headers(eTag(user.getVersion()))
				.body(dto);
	}
}
