package cz.lastware.nebudpecka.config;

import cz.lastware.nebudpecka.session.Session;
import cz.lastware.nebudpecka.user.User;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class CurrentSessionHolder {

	private Session session;
	private User user;

	public void setSession(Session session) {
		this.session = session;
		this.user = session.getUser();
	}
}
