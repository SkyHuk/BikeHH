package de.wps.bikehh.benutzerverwaltung.service;

import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService implements PasswordEncoder {

	public static final String SHA_PREFIX = "sha: ";

	private transient MessageDigest _digest;

	@Override
	public String encode(CharSequence rawPassword) {
		return encodePassword(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(encode(rawPassword));
	}

	public String encodePassword(String password) {
		String result = "#nopassword#";

		if (_digest == null) {
			try {
				_digest = MessageDigest.getInstance("SHA");
			} catch (java.security.NoSuchAlgorithmException e) {
				System.out.println(e);
			}
		}

		try {
			MessageDigest digest = (MessageDigest) _digest.clone();
			byte[] tmp = digest.digest(password.getBytes());
			result = SHA_PREFIX + Base64.encodeBytes(tmp);
		} catch (CloneNotSupportedException e) {
			System.out.println(e);
		}

		return result;
	}

}
