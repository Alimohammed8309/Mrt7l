package com.mrt7l.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class
CheckEntries {

    public static boolean isPhone(String address) {
        if (address != null && address.length() >= 10) {
            Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^(009665|9665|05)(5|0|3|6|4|9|1|8|7)([0-9]{7})$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(address);
            return matcher.find();
        } else {
            return false;
        }
    }

/*    public static boolean isUserName(String address) {
        return address != null && address.length() > 0;
    }*/

    public static boolean isValidPassword(String password) {
        return password != null && password.length() > 0;
    }

    public static boolean isSixAtLeast(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidCode(String code) {
        return code != null && code.length() > 0 && code.matches("[0-9]+");
    }

    public static boolean isMail(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
    public static String addCodeToNumber(String number) {

        if (number.startsWith("00966")||number.startsWith("+966")) {
            return number;
        }
        if (number.startsWith("00")) {
            return number.replaceFirst("00", "+966");
        }
        if (number.startsWith("+")) {
            return number.replace("+", "+966");
        }
        if (number.startsWith("0")) {
            return number.replaceFirst("0", "+966");
        }
        return number;
    }
}
