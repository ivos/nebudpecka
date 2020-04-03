package cz.lastware.nebudpecka.user;

import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.user.dto.UserDtoCreate;
import cz.lastware.nebudpecka.utils.RestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Logged(Logged.LogLevel.info)
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(
			@RequestBody UserDtoCreate dto) {
		User user = userService.create(dto);
		return ResponseEntity
				.created(RestUtils.location(user.getId()))
				.build();
	}
}
