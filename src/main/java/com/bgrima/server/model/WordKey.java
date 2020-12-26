package com.bgrima.server.model;

import com.bgrima.server.service.utils.LetterUtils;

import java.util.Objects;

public class WordKey {
    private final int syllablesCount;
    private final int lastVowel;

    public WordKey(Word word) {
        syllablesCount = word.getSyllablesCount();
        lastVowel = LetterUtils.getVowelIndex(word.getLastVowel());
    }

    public int getSyllablesCount() {
        return syllablesCount;
    }

    public int getLastVowel() {
        return lastVowel;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WordKey)) {
            return false;
        }

        WordKey wordKey = (WordKey) o;

        return syllablesCount == wordKey.getSyllablesCount() && lastVowel == wordKey.getLastVowel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(syllablesCount, lastVowel);
    }
}
