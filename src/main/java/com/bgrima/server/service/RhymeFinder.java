package com.bgrima.server.service;

import com.bgrima.server.model.Rhyme;
import com.bgrima.server.model.WeightedRhyme;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class RhymeFinder {
  private static final int MAXIMUM_RHYMES_RETURNED = 50;

  private static final long LONGEST_COMMON_SUBSEQUENCE_OF_PHONEMES_WEIGHT = 12;
  private static final long LONGEST_COMMON_SUFFIX_OF_VOWELS_WEIGHT = 10000;
  private static final long SIMILARITY_BASED_ON_BLOCKS_WEIGHT = 10;
  private static final long PENALTY_BASED_ON_SIZE_DIFFERENCE_WEIGHT = 300;

  public List<Rhyme> getRhymes(String inputWord) {
    if (!Utils.isValid(inputWord)) {
      return Collections.emptyList();
    }

    String word = Utils.transformValidWord(inputWord);
    List<String> wordBlocks = Utils.splitTransformedWord(word);
    List<WeightedRhyme> weightedRhymes = getSortedWeightedRhymes(FileLocator.getFilename(word), wordBlocks);

    return getUniqueSuffixRhymesWithPercentages(weightedRhymes, wordBlocks, inputWord);
  }

  private List<WeightedRhyme> getSortedWeightedRhymes(String filename, List<String> wordBlocks) {
    List<WeightedRhyme> weightedRhymes = new ArrayList<>();

    try (Scanner scanner = new Scanner(new File(filename))) {
      while (scanner.hasNext()) {
        String currentWord = scanner.nextLine();

        weightedRhymes.add(new WeightedRhyme(currentWord,
            rhymingValue(wordBlocks, Utils.splitTransformedWord(Utils.transformValidWord(currentWord)))));
      }
    } catch(FileNotFoundException | NullPointerException e) {
      throw new RuntimeException("No file with words found wtf fml");
    }

    Collections.sort(weightedRhymes);

    return weightedRhymes;
  }

  private List<Rhyme> getUniqueSuffixRhymesWithPercentages(
      List<WeightedRhyme> weightedRhymes, List<String> wordBlocks, String inputWord) {
    long hundredPercent = rhymingValue(wordBlocks, wordBlocks);

    List<Rhyme> rhymes = new ArrayList<>();
    for (int i = 0; i < weightedRhymes.size() && rhymes.size() < MAXIMUM_RHYMES_RETURNED; i++) {
      if (Utils.hasUniqueSuffix(inputWord, weightedRhymes.get(i).getWord(), rhymes)) {
        rhymes.add(new Rhyme(weightedRhymes.get(i).getWord(),
            calculatePercentage(weightedRhymes.get(i).getValue(), hundredPercent)));
      }
    }

    return rhymes;
  }

  private int calculatePercentage(long value, long hundredPercent) {
    double ratio = (double)(value) / (double)(hundredPercent);

    return (int)(100.0 * ratio * ratio * ratio * ratio * ratio);
  }

  private long rhymingValue(List<String> word, List<String> rhyme) {
    return rhymingValueRewards(word, rhyme) - rhymingValuePenalties(word, rhyme);
  }

  private long rhymingValueRewards(List<String> word, List<String> rhyme) {
    return longestCommonSuffixOfVowels(word, rhyme) * LONGEST_COMMON_SUFFIX_OF_VOWELS_WEIGHT
        + similarityBasedOnBlocks(word, rhyme) * SIMILARITY_BASED_ON_BLOCKS_WEIGHT;
  }

  private long rhymingValuePenalties(List<String> word, List<String> rhyme) {
    return penaltyBasedOnSizeDifference(word.size(), rhyme.size()) * PENALTY_BASED_ON_SIZE_DIFFERENCE_WEIGHT;
  }

  private long penaltyBasedOnSizeDifference(long wordSize, long rhymeSize) {
    return Math.max(wordSize, rhymeSize) - Math.min(wordSize, rhymeSize);
  }

  private long similarityBasedOnBlocks(List<String> word, List<String> rhyme) {
    long ans = 0;

    for (int i = 0; i < word.size() && i < rhyme.size(); i++) {
      int distanceToWordEnd = word.size() - i;
      ans += blockSimilarity(word.get(word.size() - i - 1), rhyme.get(rhyme.size() - i - 1)) * distanceToWordEnd;
    }

    return ans;
  }

  private long blockSimilarity(String a, String b) {
    if (a.length() == 0 || b.length() == 0) {
      return 0;
    }

    return longestCommonSubsequenceOfPhonemes(a, b) * LONGEST_COMMON_SUBSEQUENCE_OF_PHONEMES_WEIGHT - (a.length() + b.length());
  }

  private long longestCommonSuffixOfVowels(List<String> a, List<String> b) {
    int ans = 0;

    for (int i = a.size() - 2, j = b.size() - 2; i >= 0 && j >= 0; i -= 2, j -= 2) {
      if (Utils.getPhoneme(a.get(i).charAt(0)) != Utils.getPhoneme(b.get(j).charAt(0))) {
        break;
      }

      ++ans;
    }

    return ans;
  }

  private long longestCommonSubsequenceOfPhonemes(String a, String b) {
    int [][]lcs = new int [a.length()][b.length()];

    for (int i = 0; i < a.length(); i++) {
      for (int j = 0; j < b.length(); j++) {
        if (Utils.getPhoneme(a.charAt(i)) == Utils.getPhoneme(b.charAt(j))) {
          lcs[i][j] = 1;
          if (i > 0 && j > 0) {
            lcs[i][j] += lcs[i - 1][j - 1];
          }
        } else {
          lcs[i][j] = 0;
          if (i > 0) {
            lcs[i][j] = Math.max(lcs[i][j], lcs[i - 1][j]);
          }
          if (j > 0) {
            lcs[i][j] = Math.max(lcs[i][j], lcs[i][j - 1]);
          }
        }
      }
    }

    return lcs[a.length() - 1][b.length() - 1];
  }
}
