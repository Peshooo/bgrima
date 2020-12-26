package com.bgrima.server.model;

import java.util.List;

public class Word {
    private final String word;
    private final List<LetterChunk> letterChunks;
    private final int syllablesCount;
    private final char lastVowel;

    public Word(String word, List<LetterChunk> letterChunks) {
        this.word = word;
        this.letterChunks = letterChunks;

        int letterChunksCount = letterChunks.size();
        syllablesCount = letterChunksCount / 2;
        lastVowel = letterChunks.get(letterChunksCount - 2)
                .getLetters()
                .charAt(0);
    }

    public String getWord() {
        return word;
    }

    public int getLetterChunksCount() {
        return letterChunks.size();
    }

    public LetterChunk getLetterChunk(int index) {
        return letterChunks.get(index);
    }

    public int getSyllablesCount() {
        return syllablesCount;
    }

    public char getLastVowel() {
        return lastVowel;
    }
}
