package com.bgrima.server.service.utils;

import java.util.Arrays;
import java.util.List;

public class PhonemeUtils {
    private static final List<String> PHONEME_GROUPS = Arrays.asList(
            "а", "ъ", "о", "у", "е", "и", "ю", "я", "бп", "гкх", "дт", "вф", "мн", "зс", "жчцшщ", "л", "р", "йь");

    public static int getPhonemeGroup(char c) {
        return PHONEME_GROUPS.stream()
                .filter(phonemeGroup -> phonemeGroup.indexOf(c) >= 0)
                .findFirst().map(PHONEME_GROUPS::indexOf)
                .orElse(-1);
    }
}
