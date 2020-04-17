package com.bgrima.server.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

public class UtilsTest {
  @Test
  public void testSplitWords() {
    assertThat(Utils.splitWord("конструктор")).isEqualTo(
        Arrays.asList("к", "о", "нстр", "у", "кт", "о", "р"));
    assertThat(Utils.splitWord("анаеробност")).isEqualTo(
        Arrays.asList("а", "н", "а", "", "е", "р", "о", "бн", "о", "ст")); //Consecutive vowels
    assertThat(Utils.splitWord("катарама")).isEqualTo(
        Arrays.asList("к", "а", "т", "а", "р", "а", "м", "а", "")); //Trailing vowel
    assertThat(Utils.splitWord("майор")).isEqualTo(
        Arrays.asList("м", "а", "й", "о", "р"));
    assertThat(Utils.splitWord("пайнер")).isEqualTo(
        Arrays.asList("п", "а", "йн", "е", "р"));
  }
}
