package com.bgrima.server.service;

import com.bgrima.server.model.LetterChunk;
import com.bgrima.server.model.Word;

public class RhymeEvaluator {
    private static final long LONGEST_COMMON_SUFFIX_OF_VOWELS_WEIGHT = 10000;
    private static final long SIMILARITY_BASED_ON_CORRESPONDING_CHUNKS_WEIGHT = 10;
    private static final long PENALTY_BASED_ON_SIZE_DIFFERENCE_WEIGHT = 300;
    private static final long LONGEST_COMMON_SUBSEQUENCE_OF_PHONEMES_WEIGHT = 12;

    public static long evaluate(Word a, Word b) {
        return evaluateRewards(a, b) - evaluatePenalties(a, b);
    }

    private static long evaluateRewards(Word a, Word b) {
        return longestCommonSuffixOfVowels(a, b) * LONGEST_COMMON_SUFFIX_OF_VOWELS_WEIGHT
                + similarityBasedOnCorrespondingChunks(a, b) * SIMILARITY_BASED_ON_CORRESPONDING_CHUNKS_WEIGHT;
    }

    private static long longestCommonSuffixOfVowels(Word a, Word b) {
        long result = 0;

        for (int i = 2; i <= a.getLetterChunksCount() && i <= b.getLetterChunksCount(); i += 2) {
            LetterChunk vowelChunkA = a.getLetterChunk(a.getLetterChunksCount() - i);
            LetterChunk vowelChunkB = b.getLetterChunk(b.getLetterChunksCount() - i);
            char vowelA = vowelChunkA.getLetters().charAt(0);
            char vowelB = vowelChunkB.getLetters().charAt(0);

            if (vowelA == vowelB) {
                ++result;
            } else {
                break;
            }
        }

        return result;
    }

    private static long similarityBasedOnCorrespondingChunks(Word a, Word b) {
        long result = 0;

        for (int i = 1; i <= a.getLetterChunksCount() && i <= b.getLetterChunksCount(); i++) {
            long distanceToWordEnd = a.getLetterChunksCount() - i + 1;
            LetterChunk chunkA = a.getLetterChunk(a.getLetterChunksCount() - i);
            LetterChunk chunkB = b.getLetterChunk(b.getLetterChunksCount() - i);
            result += chunkSimilarity(chunkA, chunkB) * distanceToWordEnd;
        }

        return result;
    }

    private static long chunkSimilarity(LetterChunk a, LetterChunk b) {
        String lettersA = a.getLetters();
        String lettersB = b.getLetters();

        if (lettersA.isEmpty() || lettersB.isEmpty()) {
            return 0;
        }

        return LongestCommonSubsequence.longestCommonSubsequence(lettersA, lettersB) * LONGEST_COMMON_SUBSEQUENCE_OF_PHONEMES_WEIGHT
                - (lettersA.length() + lettersB.length());
    }

    private static long evaluatePenalties(Word a, Word b) {
        return penaltyBasedOnSizeDifference(a, b) * PENALTY_BASED_ON_SIZE_DIFFERENCE_WEIGHT;
    }

    private static long penaltyBasedOnSizeDifference(Word a, Word b) {
        return Math.max(a.getLetterChunksCount(), b.getLetterChunksCount())
                - Math.min(a.getLetterChunksCount(), b.getLetterChunksCount());
    }
}
