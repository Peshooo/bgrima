package com.bgrima.server.service.utils;

public class LetterUtils {
    private static final String UPPERCASE_LETTERS = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЬЮЯ";
    private static final String LOWERCASE_LETTERS = "абвгдежзийклмнопрстуфхцчшщъьюя";
    private static final String ALLOWED_NON_LETTERS = ".,?!()- ";
    private static final String VOWELS = "аъоуеиюя";

    public static int getVowelIndex(char c) {
        return VOWELS.indexOf(c);
    }

    public static boolean isVowel(char c) {
        return VOWELS.indexOf(c) != -1;
    }

    public static char toLowercase(char c) {
        int index = UPPERCASE_LETTERS.indexOf(c);

        return index == -1
                ? c
                : LOWERCASE_LETTERS.charAt(index);
    }

    public static boolean isAllowed(char c) {
        return UPPERCASE_LETTERS.indexOf(c) != -1
                || LOWERCASE_LETTERS.indexOf(c) != -1
                || ALLOWED_NON_LETTERS.indexOf(c) != -1;
    }
}
