package cz.lastware.nebudpecka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final SecurityUserDetailsService userDetailsService;

	public SecurityConfig(SecurityUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(new MatchingPasswordEncoder());
		return authenticationProvider;
	}

	@Bean
	public BasicAuthenticationEntryPoint securityExceptionBasicEntryPoint() {
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("Backend");
		return entryPoint;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers("/api/register", "/api/login").anonymous()
				.anyRequest().authenticated()
				.and()
				.httpBasic()
				.authenticationEntryPoint(securityExceptionBasicEntryPoint())
				.and()
				.logout().logoutRequestMatcher(request -> false) // disable Spring auto-conf logout handler
				.and()
				.exceptionHandling().authenticationEntryPoint(securityExceptionBasicEntryPoint())
		;
	}

	public static class MatchingPasswordEncoder implements PasswordEncoder {
		@Override
		public String encode(CharSequence rawPassword) {
			return "";
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			return true;
		}
	}
}
