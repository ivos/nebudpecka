package cz.lastware.nebudpecka.user;

import cz.lastware.nebudpecka.config.Logged;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Logged
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
