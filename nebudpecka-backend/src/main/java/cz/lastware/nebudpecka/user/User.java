package cz.lastware.nebudpecka.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(of = "id")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;

	@NotNull
	@Size(min = 1, max = 100)
	private String name;

	@NotNull
	@Size(min = 1, max = 100)
	@Email
	private String email;

	@NotNull
	@Size(min = 1, max = 100)
	private String passwordHash;
}
