package com.example.taskmanagementproject.util;

import java.util.regex.Pattern;

public class EmailValidation {

    public static boolean patternMatches(String emailAddress) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex)
                .matcher(emailAddress)
                .matches();
    }
}
