package cz.lastware.nebudpecka.time;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TimeService {

	public LocalDateTime now() {
		return LocalDateTime.now();
	}

	public LocalDate today() {
		return LocalDate.now();
	}
}
