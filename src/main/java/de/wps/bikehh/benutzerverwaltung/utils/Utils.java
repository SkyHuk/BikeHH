package de.wps.bikehh.benutzerverwaltung.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class Utils {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static final int TOKEN_COUNT = 60;

    public static String generateSecureToken(int count) {
        byte[] randomBytes = new byte[count];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}