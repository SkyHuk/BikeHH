package de.wps.bikehh.config;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecureRandom secureRandom() {
		return new SecureRandom();
	}

	@Bean
	public Encoder base64Encoder() {
		return Base64.getEncoder();
	}

}
