package cz.lastware.nebudpecka.user;

import cz.lastware.nebudpecka.config.Logged;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Logged(Logged.LogLevel.info)
public class UserRestController {
}
