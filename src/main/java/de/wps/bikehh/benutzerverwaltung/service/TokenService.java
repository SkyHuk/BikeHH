package de.wps.bikehh.benutzerverwaltung.service;

import java.security.SecureRandom;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	private static final int TOKEN_COUNT = 32;

	private SecureRandom secureRandom;
	private Encoder base64Encoder;

	public TokenService(SecureRandom secureRandom, Encoder base64Encoder) {
		this.secureRandom = secureRandom;
		this.base64Encoder = base64Encoder;
	}

	public String generateSecureToken() {
		byte[] randomBytes = new byte[TOKEN_COUNT];
		secureRandom.nextBytes(randomBytes);
		return base64Encoder.encodeToString(randomBytes);
	}
}
