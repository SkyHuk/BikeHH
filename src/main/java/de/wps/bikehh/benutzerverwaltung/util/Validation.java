package de.wps.bikehh.benutzerverwaltung.util;

public class Validation {
    public static boolean isEmailValid(String email) {
        //String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
    }

    public static boolean isPasswordValid(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,64}$";
        return password.matches(regex);
    }

    /*
    //Ein Passwort muss mindestens 8 Zeichen lang sein und darf nur aus Buchstaben und zahlen bestehen,
    //wobei es dabei mindestens eine Zahl enthalten muss.
    public static boolean isPasswordValid(String password) {
        if (password.length() < 8 | password.length() > 64)
            return false;

        int charCount = 0;
        int numCount = 0;

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }


        return (numCount >= 1);
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }*/

}
