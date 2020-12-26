package com.bgrima.server.model;

public class Rhyme {
    private final String word;
    private final long value;

    public Rhyme(String word, long value) {
        this.word = word;
        this.value = value;
    }

    public String getWord() {
        return word;
    }

    public long getValue() {
        return value;
    }
}
