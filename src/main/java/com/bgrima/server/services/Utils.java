package com.bgrima.server.services;

import java.util.ArrayList;
import java.util.List;

import static com.bgrima.server.models.Constants.*;

public class Utils {
  public static boolean isValid(String word) {
    if (word.length() > 30) {
      return false;
    }

    boolean hasVowel = false;

    for (int i = 0; i < word.length(); i++) {
      char curr = word.charAt(i);

      if (isLowercaseLetter(curr)) {
        if (isVowel(curr)) {
          hasVowel = true;
        }

        continue;
      }

      if (isUppercaseLetter(curr)) {
        if (isVowel(toLowercase(curr))) {
          hasVowel = true;
        }

        continue;
      }

      if (!isAllowedNonLetter(curr)) {
        return false;
      }
    }

    return hasVowel;
  }

  //Assumes word is valid
  public static String transform(String word) {
    String ans = "";

    for (int i = 0; i < word.length(); i++) {
      char curr = word.charAt(i);

      if (isLowercaseLetter(curr)) {
        ans += curr;
      } else if (isUppercaseLetter(curr)) {
        ans += toLowercase(curr);
      }
    }

    return ans;
  }

  //Assumes it's uppercase
  public static char toLowercase(char c) {
    return LOWERCASE_LETTERS.charAt(UPPERCASE_LETTERS.indexOf(c));
  }

  public static boolean isLowercaseLetter(char c) {
    return LOWERCASE_LETTERS.indexOf(c) >= 0;
  }

  public static boolean isUppercaseLetter(char c) {
    return UPPERCASE_LETTERS.indexOf(c) >= 0;
  }

  public static boolean isAllowedNonLetter(char c) {
    return ALLOWED_NON_LETTERS.indexOf(c) >= 0;
  }

  public static boolean isVowel(char c) {
    return VOWELS.indexOf(c) >= 0;
  }

  /*
    Splits the string into alternating blocks of vowels and consonants.
    Assumes the word is valid and transformed already.
    Empty consonant blocks are inserted to ensure that every
  two consecutive blocks are from different kind and the last one is
  a block of consonants.
  */
  public static List<String> splitWord(String word) {
    List<String> ans = new ArrayList<>();
    char last = 'q'; //Random non-cyrillic character

    for (int i = 0; i < word.length(); i++) {
      char curr = word.charAt(i);
      String currString = ""; //Current block
      currString += curr;

      if (isVowel(curr)) {
        if (isVowel(last)) {
          ans.add(""); //Empty block of consonants
        }
        last = curr;
      } else {
        last = curr; //Notice that it's only the type that matters - consonant or vowel

        for (i++; i < word.length(); i++) {
          if (isVowel(word.charAt(i))) {
            break;
          }

          currString += word.charAt(i);
        }

        --i;
      }

      ans.add(currString);
    }

    if (isVowel(last)) {
      ans.add(""); //Empty block of consonants
    }

    return ans;
  }
  
  public static int getPhoneme(char c) {
    if (c == 'а') {
      return 1;
    }
    if (c == 'ъ') {
      return 2;
    }
    if (c == 'о') {
      return 3;
    }
    if (c == 'у') {
      return 4;
    }
    if (c == 'е') {
      return 5;
    }
    if (c == 'и') {
      return 6;
    }
    if (c == 'ю') {
      return 7;
    }
    if (c == 'я') {
      return 8;
    }
    if (c == 'б' || c == 'п') {
      return 15;
    }
    if (c == 'г' || c == 'к' || c == 'х') {
      return 16;
    }
    if (c == 'д' || c == 'т') {
      return 17;
    }
    if (c == 'в' || c == 'ф') {
      return 18;
    }
    if (c == 'м' || c == 'н') {
      return 19;
    }
    if (c == 'з' || c == 'с') {
      return 20;
    }
    if (c == 'ж' || c == 'ч' || c == 'ц' || c == 'ш' || c == 'щ') {
      return 21;
    }
    if (c == 'л') {
      return 22;
    }
    if (c == 'р') {
      return 23;
    }
    if (c == 'й' || c == 'ь') {
      return 24;
    }

    //UNDEFINED
    return 25;
  }
}
