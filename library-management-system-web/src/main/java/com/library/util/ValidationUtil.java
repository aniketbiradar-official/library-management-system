package com.library.util;

public class ValidationUtil {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidISBN(String isbn) {
        if (isbn == null) return false;
        return isbn.matches("\\d{10}|\\d{13}");
    }

    public static boolean isPositiveNumber(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean maxLength(String value, int max) {
        return value != null && value.length() <= max;
    }
}
