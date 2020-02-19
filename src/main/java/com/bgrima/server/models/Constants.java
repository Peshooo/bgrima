package com.bgrima.server.models;

import java.util.Arrays;
import java.util.List;

public class Constants {
  public static final String WORDS_PATH_PREFIX = "words/data_";
  public static final String WORDS_PATH_SUFFIX = ".txt";

  public static final int MAXIMUM_SYLLABLES = 10; //TODO: Merge 10+ syllabic words in a single file
  public static final List<Integer> SINGLE_GROUPS = Arrays.asList(1, 2, 9, 10);
  public static final List<Integer> MULTIPLE_GROUPS = Arrays.asList(3, 4, 5, 6, 7, 8);

  public static final String VOWELS = "аъоуеиюя";

  public static final String LOWERCASE_LETTERS = "абвгдежзийклмнопрстуфхцчшщъьюя";
  public static final String UPPERCASE_LETTERS = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЬЮЯ";
  public static final String ALLOWED_NON_LETTERS = ".,?!()- ";
}
