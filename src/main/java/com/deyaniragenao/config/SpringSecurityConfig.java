package com.deyaniragenao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SpringSecurityConfig is a configuration class that provides the necessary configuration for authentication 
 * and authorization for the application. 
 * @author deyaniragenao
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	/**
	 * Returns a bean of the BCryptPasswordEncoder used to encode user passwords.
	 * @return passwordEncoder
	 */
	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Returns the SecurityFilterChain that manages how users are authenticated (login and logout processing) 
	 * and authorization of different views. As this is a learning project, any user is allowed to view any of the pages.
	 * Soon to be updated by creating additional antMatchers where authorization is granted to users with ROLE_USER
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/**", "/css/**").permitAll()
					.and()
					.formLogin(form -> form.loginPage("/signin")
											.loginProcessingUrl("/signin")
											.defaultSuccessUrl("/home/")
											.permitAll()
					).logout(logout -> logout
										.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
										.permitAll());
		
		return http.build();
				
		
	}
}
