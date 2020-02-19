package com.bgrima.server.services;

import static com.bgrima.server.models.Constants.*;

public class FileLocator {

  //Assumes it's a valid and transformed word
  public static String getFilename(String word) {
    int vowelsCount = 0;
    char lastVowel = '?';

    for (int i = 0; i < word.length(); i++) {
      char curr = word.charAt(i);

      if (Utils.isVowel(curr)) {
        ++vowelsCount;
        lastVowel = curr;
      }
    }

    vowelsCount = Math.min(vowelsCount, MAXIMUM_SYLLABLES);

    if(SINGLE_GROUPS.contains(vowelsCount)) {
      return WORDS_PATH_PREFIX + vowelsCount + WORDS_PATH_SUFFIX;
    }

    return WORDS_PATH_PREFIX + vowelsCount + "_" + VOWELS.indexOf(lastVowel) + WORDS_PATH_SUFFIX;
  }
}
