package cz.lastware.nebudpecka.my_user;

import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoCreate;
import cz.lastware.nebudpecka.user.User;
import cz.lastware.nebudpecka.utils.RestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-user")
@Logged(Logged.LogLevel.info)
public class MyUserRestController {

	private final MyUserService myUserService;

	public MyUserRestController(MyUserService myUserService) {
		this.myUserService = myUserService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> register(
			@RequestBody MyUserDtoCreate dto) {
		User user = myUserService.register(dto);
		return ResponseEntity
				.created(RestUtils.location(user.getId()))
				.build();
	}
}
