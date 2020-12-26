package com.bgrima.server.service.utils;

import com.bgrima.server.model.LetterChunk;
import com.bgrima.server.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordUtils {
    private static final int MAXIMUM_WORD_LENGTH = 30;

    public static boolean isValid(String word) {
        if (word.length() > MAXIMUM_WORD_LENGTH) {
            return false;
        }

        return allCharactersAllowed(word) && hasVowel(word);
    }

    private static boolean allCharactersAllowed(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!LetterUtils.isAllowed(word.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasVowel(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!LetterUtils.isVowel(word.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public static Word transform(String word) {
        String wordNoSpaces = word.replace(" ", "");
        String wordLowercase = toLowercase(wordNoSpaces);

        List<LetterChunk> letterChunks = new ArrayList<>();
        boolean isLastVowel = false;

        for (int i = 0; i < wordLowercase.length(); i++) {
            char letter = wordLowercase.charAt(i);

            if (LetterUtils.isVowel(letter)) {
                if (isLastVowel) {
                    letterChunks.add(new LetterChunk(""));
                }

                letterChunks.add(new LetterChunk(String.valueOf(letter)));

                isLastVowel = true;
            } else {
                int chunkEnd = i;

                while (chunkEnd < wordLowercase.length() && !LetterUtils.isVowel(wordLowercase.charAt(chunkEnd))) {
                    ++chunkEnd;
                }

                String chunk = wordLowercase.substring(i, chunkEnd);
                letterChunks.add(new LetterChunk(chunk));

                i = chunkEnd - 1;

                isLastVowel = false;
            }
        }

        if (isLastVowel) {
            letterChunks.add(new LetterChunk(""));
        }

        return new Word(word, letterChunks);
    }

    private static String toLowercase(String word) {
        String lowercaseWord = "";

        for (int i = 0; i < word.length(); i++) {
            lowercaseWord += LetterUtils.toLowercase(word.charAt(i));
        }

        return lowercaseWord;
    }

    public static boolean haveSameSuffix(String a, String b) {
        for (int i = 0; i < a.length() && i < b.length(); i++) {
            char letterA = a.charAt(a.length() - i - 1);
            char letterB = b.charAt(b.length() - i - 1);

            if (letterA != letterB) {
                return false;
            }

            if (letterA == ' ') {
                return true;
            }
        }

        return true;
    }
}
