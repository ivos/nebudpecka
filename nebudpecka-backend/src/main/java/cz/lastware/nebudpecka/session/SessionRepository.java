package cz.lastware.nebudpecka.session;

import cz.lastware.nebudpecka.config.Logged;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Logged
public interface SessionRepository extends JpaRepository<Session, UUID> {
}
