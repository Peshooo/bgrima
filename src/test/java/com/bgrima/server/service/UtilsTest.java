package com.bgrima.server.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

class UtilsTest {
  @Test
  void testSplitWords() {
    assertThat(Utils.splitTransformedWord("конструктор")).isEqualTo(
        Arrays.asList("к", "о", "нстр", "у", "кт", "о", "р"));
    assertThat(Utils.splitTransformedWord("анаеробност")).isEqualTo(
        Arrays.asList("а", "н", "а", "", "е", "р", "о", "бн", "о", "ст")); //Consecutive vowels
    assertThat(Utils.splitTransformedWord("катарама")).isEqualTo(
        Arrays.asList("к", "а", "т", "а", "р", "а", "м", "а", "")); //Trailing vowel
    assertThat(Utils.splitTransformedWord("майор")).isEqualTo(
        Arrays.asList("м", "а", "й", "о", "р"));
    assertThat(Utils.splitTransformedWord("пайнер")).isEqualTo(
        Arrays.asList("п", "а", "йн", "е", "р"));
  }
}
