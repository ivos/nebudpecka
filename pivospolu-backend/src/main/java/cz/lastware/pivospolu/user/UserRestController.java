package cz.lastware.pivospolu.user;

import cz.lastware.pivospolu.user.dto.UserDtoCreate;
import cz.lastware.pivospolu.utils.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(
			@RequestBody UserDtoCreate dto) {
		log.info("Create user {}", dto);
		User user = userService.create(dto);
		return ResponseEntity
				.created(RestUtils.location(user.getId()))
				.build();
	}
}
