package de.wps.bikehh.benutzerverwaltung.util;

public class Validation {
    public static boolean isEmailValid(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
    }

    public static boolean isPasswordValid(String password) {
        String regex = "^(?=.*\\d)[a-zA-Z\\d]{8,64}$";
        return password.matches(regex);
    }
}
