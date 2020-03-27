package cz.lastware.nebudpecka.user;

import cz.lastware.nebudpecka.user.dto.UserDtoCreate;
import cz.lastware.nebudpecka.validation.Validation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final UserRepository repo;
	private final MapperFacade mapper;
	private final Validation validation;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository repo, MapperFacade mapper, Validation validation,
			PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.mapper = mapper;
		this.validation = validation;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User create(UserDtoCreate dto) {
		validation.verifyBean(dto);

		User user = mapper.map(dto, User.class);
		String passwordHash = passwordEncoder.encode(dto.getPassword());
		user.setPasswordHash(passwordHash);

		user = repo.saveAndFlush(user);
		return user;
	}
}
