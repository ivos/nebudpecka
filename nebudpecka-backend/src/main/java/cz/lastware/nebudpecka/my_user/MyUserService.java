package cz.lastware.nebudpecka.my_user;

import cz.lastware.nebudpecka.config.Logged;
import cz.lastware.nebudpecka.my_user.dto.MyUserDtoCreate;
import cz.lastware.nebudpecka.user.User;
import cz.lastware.nebudpecka.user.UserRepository;
import cz.lastware.nebudpecka.validation.Validation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public User register(MyUserDtoCreate dto) {
		validation.verifyBean(dto);

		User user = mapper.map(dto, User.class);
		String passwordHash = passwordEncoder.encode(dto.getPassword());
		user.setPasswordHash(passwordHash);

		user = repo.saveAndFlush(user);
		return user;
	}
}
