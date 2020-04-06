package cz.lastware.nebudpecka.session;

import cz.lastware.nebudpecka.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Data
@EqualsAndHashCode(of = "token")
public class Session {

	@Id
	private UUID token;

	@NotNull
	private LocalDateTime created;

	@NotNull
	private Integer duration;

	@NotNull
	private LocalDateTime expires;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Override
	public String toString() {
		return "Session{" +
				"token=" + token +
				", created=" + created +
				", duration=" + duration +
				", expires=" + expires +
				", user=" + ((user == null) ? null : user.getId()) +
				'}';
	}
}
