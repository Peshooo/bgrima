package com.bgrima.server.service;

import com.bgrima.server.model.Rhyme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Utils {
  private static final int MAXIMUM_WORD_LENGTH = 30;

  private static final List<String> PHONEME_GROUPS = Arrays.asList(
      "а", "ъ", "о", "у", "е", "и", "ю", "я", "бп", "гкх", "дт", "вф", "мн", "зс", "жчцшщ", "л", "р", "йь");

  private static final String LOWERCASE_LETTERS = "абвгдежзийклмнопрстуфхцчшщъьюя";
  private static final String UPPERCASE_LETTERS = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЬЮЯ";
  private static final String ALLOWED_NON_LETTERS = ".,?!()- ";

  static final String VOWELS = "аъоуеиюя";

  static final String WORDS_PATH_PREFIX = "words/data_";
  static final String WORDS_PATH_SUFFIX = ".txt";

  static final int MAXIMUM_SYLLABLES = 10; //TODO: Merge 10+ syllabic words in a single file
  static final List<Integer> SINGLE_GROUPS = Arrays.asList(1, 2, 9, 10);

  private Utils() {
  }

  static boolean isValid(String word) {
    if (word.length() > MAXIMUM_WORD_LENGTH) {
      return false;
    }

    boolean hasVowel = false;

    for (int i = 0; i < word.length(); i++) {
      if (!isValidCharacter(word.charAt(i))) {
        return false;
      }

      if (isVowel(toLowercase(word.charAt(i)))) {
        hasVowel = true;
      }
    }

    return hasVowel;
  }

  /*
    Splits the string into alternating blocks of vowels and consonants.
    Assumes the word is valid and transformed already.
    Empty consonant blocks are inserted to ensure that every
  two consecutive blocks are from different kind and the last one is
  a block of consonants.
  */
  static List<String> splitTransformedWord(String word) {
    List<String> ans = new ArrayList<>();
    splitWord(word, 0, false, ans);

    return ans;
  }

  static int getPhoneme(char c) {
    for (int i = 0; i < PHONEME_GROUPS.size(); i++) {
      if (PHONEME_GROUPS.get(i).indexOf(c) != -1) {
        return i;
      }
    }

    return PHONEME_GROUPS.size();
  }

  static boolean hasUniqueSuffix(String inputWord, String currentRhyme, List<Rhyme> rhymes) {
    return !haveSameSuffix(inputWord, currentRhyme)
        && rhymes.stream().noneMatch(rhyme -> haveSameSuffix(rhyme.getWord(), currentRhyme));
  }

  static String transformValidWord(String word) {
    StringBuilder transformedWord = new StringBuilder();

    for (int i = 0; i < word.length(); i++) {
      transformedWord.append(toLowercase(word.charAt(i)));
    }

    return transformedWord.toString();
  }

  static boolean isVowel(char c) {
    return VOWELS.indexOf(c) != -1;
  }

  private static boolean isValidCharacter(char c) {
    return isLowercaseLetter(c) || isUppercaseLetter(c) || isAllowedNonLetter(c);
  }

  private static char toLowercase(char c) {
    int letterIdx = UPPERCASE_LETTERS.indexOf(c);
    return letterIdx == -1 ? c : LOWERCASE_LETTERS.charAt(letterIdx);
  }

  private static boolean isLowercaseLetter(char c) {
    return LOWERCASE_LETTERS.indexOf(c) != -1;
  }

  private static boolean isUppercaseLetter(char c) {
    return UPPERCASE_LETTERS.indexOf(c) != -1;
  }

  private static boolean isAllowedNonLetter(char c) {
    return ALLOWED_NON_LETTERS.indexOf(c) != -1;
  }

  private static void splitWord(String word, int idx, boolean isLastVowel, List<String> blocks) {
    if (idx >= word.length()) {
      if (isLastVowel) {
        blocks.add("");
      }

      return;
    }

    if (isVowel(word.charAt(idx))) {
      splitWord(word, getIndexAfterAddingVowelBlock(word, idx, isLastVowel, blocks), true, blocks);
      return;
    }

    splitWord(word, getIndexAfterAddingConsonantBlock(word, idx, blocks), false, blocks);
  }

  private static int getIndexAfterAddingVowelBlock(String word, int idx, boolean isLastVowel, List<String> blocks) {
    if (isLastVowel) {
      blocks.add("");
    }

    blocks.add(String.valueOf(word.charAt(idx)));
    return idx + 1;
  }

  private static int getIndexAfterAddingConsonantBlock(String word, int idx, List<String> blocks) {
    StringBuilder currentBlock = new StringBuilder();

    for (; idx < word.length(); idx++) {
      if (isVowel(word.charAt(idx))) {
        break;
      }

      currentBlock.append(word.charAt(idx));
    }

    blocks.add(currentBlock.toString());

    return idx;
  }

  private static boolean haveSameSuffix(String a, String b) {
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
