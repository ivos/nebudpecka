package cz.lastware.nebudpecka.my_user;

import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoRegister;
import cz.lastware.nebudpecka.user.User;
import cz.lastware.nebudpecka.user.UserRepository;
import cz.lastware.nebudpecka.validation.Validation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Logged
public class MyUserService {

	private final UserRepository repo;
	private final MapperFacade mapper;
	private final Validation validation;
	private final PasswordEncoder passwordEncoder;

	public MyUserService(UserRepository repo, MapperFacade mapper, Validation validation,
			PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.mapper = mapper;
		this.validation = validation;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User register(MyUserDtoRegister dto) {
		validation.verifyBean(dto);

		User user = mapper.map(dto, User.class);
		String passwordHash = passwordEncoder.encode(dto.getPassword());
		user.setPasswordHash(passwordHash);

		user = repo.saveAndFlush(user);
		return user;
	}

	@Transactional(readOnly = true)
	public User getInLogin(String email, String password) {
		User user = repo.findByEmail(email)
				.orElseThrow(EntityNotFoundException::new);
		boolean matches = passwordEncoder.matches(password, user.getPasswordHash());
		if (!matches) {
			throw new BadCredentialsException("Invalid password.");
		}
		return user;
	}
}
