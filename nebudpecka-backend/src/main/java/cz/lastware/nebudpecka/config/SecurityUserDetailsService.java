package cz.lastware.nebudpecka.config;

import cz.lastware.nebudpecka.session.Session;
import cz.lastware.nebudpecka.session.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Logged
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {

	private final SessionService sessionService;
	private final CurrentSessionHolder currentSessionHolder;

	public SecurityUserDetailsService(SessionService sessionService, CurrentSessionHolder currentSessionHolder) {
		this.sessionService = sessionService;
		this.currentSessionHolder = currentSessionHolder;
	}

	@Override
	public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
		Session session = sessionService.getValidSession(token)
				.orElseThrow(() ->
						new UsernameNotFoundException("A valid session with token [" + token + "] was not found."));
		currentSessionHolder.setSession(session);
		log.debug("Authenticated token {} as {}, {}", token, session, session.getUser());
		return org.springframework.security.core.userdetails.User
				.withUsername(session.getToken().toString())
				.password("")
				.authorities(Collections.emptyList())
				.build();
	}
}
