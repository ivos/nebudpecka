package cz.lastware.nebudpecka.my_user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MyUserDtoLogin {

	@NotNull
	private String email;

	@NotNull
	private String password;
}
