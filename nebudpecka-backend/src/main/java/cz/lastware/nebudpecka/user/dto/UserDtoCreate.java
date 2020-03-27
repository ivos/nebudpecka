package cz.lastware.nebudpecka.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDtoCreate {

	@NotNull
	@Size(min = 1, max = 100)
	private String name;

	@NotNull
	@Size(min = 1, max = 100)
	@Email
	private String email;

	@NotNull
	@Size(min = 8)
	private String password;
}
