package com.bgrima.server.services;

import com.bgrima.server.models.Rhyme;
import com.bgrima.server.models.WeightedRhyme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Service
public class RhymeEngine {
  private static final int MAXIMUM_RHYMES_RETURNED = 30;
  Logger log = LoggerFactory.getLogger(RhymeEngine.class);

  public List<Rhyme> getRhymes(String word) throws FileNotFoundException {
    if (!Utils.isValid(word)) {
      return Collections.emptyList();
    }

    String initialWord = word;

    word = Utils.transform(word);

    Scanner scanner = new Scanner(
        new File(getClass()
            .getClassLoader()
            .getResource(FileLocator.getFilename(word))
            .getFile()));

    List<String> blockWord = Utils.splitWord(word);
    List<WeightedRhyme> weightedRhymes = new ArrayList<>();

    while (scanner.hasNext()) {
      String currentWord = scanner.nextLine();

      weightedRhymes.add(new WeightedRhyme(currentWord,
          rhymingValue(blockWord, Utils.splitWord(Utils.transform(currentWord)))));
    }

    Collections.sort(weightedRhymes);

    long hundredPercent = rhymingValue(blockWord, blockWord);

    List<Rhyme> rhymes = new ArrayList<>();
    for (int i = 0; i < weightedRhymes.size() && rhymes.size() < MAXIMUM_RHYMES_RETURNED; i++) {
      if (isGood(initialWord, weightedRhymes.get(i).getWord(), rhymes)) {
        rhymes.add(new Rhyme(weightedRhymes.get(i).getWord(),
            calculatePercentage(weightedRhymes.get(i).getValue(), hundredPercent)));
      }
    }

    return rhymes;
  }

  private boolean isSuffix(String a, String b) {
    for (int i = 0; i < a.length() && i < b.length(); i++) {
      if (a.charAt(a.length() - i - 1) != b.charAt(b.length() - i - 1)) {
        return false;
      }

      if (a.charAt(a.length() - i - 1) == ' ' && b.charAt(b.length() - i - 1) == ' ') {
        return true; //Looks strange a hack but we do not want the same words trailing
      }
    }

    return true;
  }

  private boolean isGood(String initialWord, String rhyme, List<Rhyme> rhymes) {
    if (isSuffix(initialWord, rhyme)) {
      return false;
    }

    for (int i = 0; i < rhymes.size(); i++) {
      if (isSuffix(rhymes.get(i).getWord(), rhyme)) {
        return false;
      }
    }

    return true;
  }


  private int calculatePercentage(long value, long hundredPercent) {
    double ratio = (double)(value) / (double)(hundredPercent);

    return (int)(100.0 * ratio * ratio * ratio * ratio * ratio);
  }

  //Accepts two split words
  //TODO: Create a block model (interface with vowel and consonant implementations)
  private long rhymingValue(List<String> a, List<String> b) {
    long ans = 0;
    int matchingVowels = 0;

    for (int i = a.size() - 2, j = b.size() - 2; i >= 0 && j >= 0; i -= 2, j -= 2) {
      if (Utils.getPhoneme(a.get(i).charAt(0)) == Utils.getPhoneme(b.get(j).charAt(0))) {
        ++matchingVowels;
      } else {
        break;
      }
    }

    //TODO: At least pretend there is science behind this, bring it out as constant
    ans += matchingVowels * 1000L;

    for (int i = 0; i < a.size() && i < b.size(); i++) {
      //TODO: WTF?
      ans += blockSimilarity(a.get(a.size() - i - 1), b.get(b.size() - i - 1)) * 1L * (a.size() - i);
    }

    //Now this looks scientific
    ans *= 10L;
    ans -= 300L * Math.max(a.size() - b.size(), b.size() - a.size());

    return ans;
  }

  //Measures how similar two blocks of the same kind (consonants or vowels) are
  //TODO: Create a block model (interface with vowel and consonant implementations)
  private long blockSimilarity(String a, String b) {
    if (a.length() == 0 || b.length() == 0) {
      return 0;
    }

    int lcs[][] = new int[a.length()][b.length()];

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

    long ans = 0;

    //No real science behind this
    //TODO: Declare constants for weights
    ans += lcs[a.length() - 1][b.length() - 1] * 12L;
    ans -= (a.length() + b.length());

    return ans;
  }
}
