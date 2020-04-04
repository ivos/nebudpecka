package cz.lastware.nebudpecka.session;

import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.my_user.MyUserService;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoLogin;
import cz.lastware.nebudpecka.time.TimeService;
import cz.lastware.nebudpecka.user.User;
import cz.lastware.nebudpecka.validation.Validation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Logged
public class SessionService {

	@Value("${app.session.default-duration.min:90}")
	int sessionDefaultDuration;

	private final SessionRepository repo;
	private final Validation validation;
	private final MyUserService myUserService;
	private final TimeService timeService;

	public SessionService(SessionRepository repo, Validation validation,
			MyUserService myUserService, TimeService timeService) {
		this.repo = repo;
		this.validation = validation;
		this.myUserService = myUserService;
		this.timeService = timeService;
	}

	@Transactional
	public Session login(MyUserDtoLogin dto) {
		validation.verifyBean(dto);

		User user = myUserService.getInLogin(dto.getEmail(), dto.getPassword());

		LocalDateTime now = timeService.now();
		Session session = new Session();

		session.setToken(UUID.randomUUID());
		session.setCreated(now);
		session.setDuration(sessionDefaultDuration);
		session.setExpires(now.plusMinutes(session.getDuration()));
		session.setUser(user);

		session = repo.saveAndFlush(session);
		return session;
	}
}
