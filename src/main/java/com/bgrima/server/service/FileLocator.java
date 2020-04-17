package com.bgrima.server.service;

import static com.bgrima.server.service.Utils.*;

class FileLocator {
  private FileLocator() {
  }

  static String getFilename(String word) {
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
