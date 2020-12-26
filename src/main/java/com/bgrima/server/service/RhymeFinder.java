package com.bgrima.server.service;

import com.bgrima.server.model.Rhyme;
import com.bgrima.server.model.Word;
import com.bgrima.server.model.WordKey;
import com.bgrima.server.service.utils.WordUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RhymeFinder {
    private static final int MAXIMUM_RHYMES_RETURNED = 50;

    public static List<Rhyme> getRhymes(String word) {
        if (!WordUtils.isValid(word)) {
            return Collections.emptyList();
        }

        return getRhymes(WordUtils.transform(word));
    }

    private static List<Rhyme> getRhymes(Word word) {
        WordKey wordKey = new WordKey(word);
        List<Word> candidates = Dictionary.getByKey(wordKey);

        if (candidates.isEmpty()) {
            return Collections.emptyList();
        }

        List<Rhyme> rhymes = buildRhymes(word, candidates);
        List<Rhyme> uniqueSuffixRhymes = getUniqueSuffixRhymes(word.getWord(), rhymes);

        return transformValuesToPercentages(uniqueSuffixRhymes);
    }

    private static List<Rhyme> buildRhymes(Word word, List<Word> candidates) {
        return candidates.stream()
                .map(candidate -> new Rhyme(candidate.getWord(), RhymeEvaluator.evaluate(word, candidate)))
                .sorted(Collections.reverseOrder(Comparator.comparing(Rhyme::getValue)))
                .collect(Collectors.toList());
    }

    private static List<Rhyme> getUniqueSuffixRhymes(String inputWord, List<Rhyme> rhymes) {
        List<Rhyme> uniqueSuffixRhymes = new ArrayList<>();

        for (Rhyme rhyme : rhymes) {
            if (!hasUniqueSuffix(inputWord, uniqueSuffixRhymes, rhyme)) {
                continue;
            }

            uniqueSuffixRhymes.add(rhyme);

            if (uniqueSuffixRhymes.size() == MAXIMUM_RHYMES_RETURNED) {
                break;
            }
        }

        return uniqueSuffixRhymes;
    }

    private static boolean hasUniqueSuffix(String inputWord, List<Rhyme> currentRhymes, Rhyme rhyme) {
        return !WordUtils.haveSameSuffix(inputWord, rhyme.getWord()) && hasUniqueSuffix(currentRhymes, rhyme);
    }

    private static boolean hasUniqueSuffix(List<Rhyme> currentRhymes, Rhyme rhyme) {
        return currentRhymes.stream()
                .map(Rhyme::getWord)
                .noneMatch(word -> WordUtils.haveSameSuffix(word, rhyme.getWord()));
    }

    private static List<Rhyme> transformValuesToPercentages(List<Rhyme> rhymes) {
        long hundredPercent = rhymes.get(0).getValue();

        return rhymes.stream()
                .map(rhyme -> transformValueToPercentage(rhyme, hundredPercent))
                .collect(Collectors.toList());
    }

    private static Rhyme transformValueToPercentage(Rhyme rhyme, long hundredPercent) {
        double ratio = (double) rhyme.getValue() / (double) hundredPercent;
        long percentage = (int) (100.0 * ratio * ratio * ratio * ratio * ratio);

        return new Rhyme(rhyme.getWord(), percentage);
    }
}
