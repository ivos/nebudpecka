package cz.lastware.nebudpecka.session;

import cz.lastware.nebudpecka.config.CurrentSessionHolder;
import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.my_user.MyUserService;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoLogin;
import cz.lastware.nebudpecka.time.TimeService;
import cz.lastware.nebudpecka.user.User;
import cz.lastware.nebudpecka.validation.Validation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
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
	private final CurrentSessionHolder currentSessionHolder;

	public SessionService(SessionRepository repo, Validation validation, MyUserService myUserService,
			TimeService timeService, CurrentSessionHolder currentSessionHolder) {
		this.repo = repo;
		this.validation = validation;
		this.myUserService = myUserService;
		this.timeService = timeService;
		this.currentSessionHolder = currentSessionHolder;
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

	@Transactional(readOnly = true)
	public Optional<Session> getValidSession(String token) {
		LocalDateTime now = timeService.now();

		Optional<Session> session = repo.findById(UUID.fromString(token));

		if (session.isPresent() && session.get().getExpires().isBefore(now)) {
			return Optional.empty();
		}
		return session;
	}

	@Transactional(readOnly = true)
	public Session getSession(UUID token) {
		return repo.findById(token)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public void logout() {
		LocalDateTime now = timeService.now();
		Session currentSession = currentSessionHolder.getSession();

		Session session = getSession(currentSession.getToken());

		session.setExpires(now);
		repo.flush();
	}
}
