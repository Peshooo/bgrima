package com.bgrima.server.models;

public class Rhyme {
  private String word;
  private int percentage;

  public Rhyme(String word, int percentage) {
    this.word = word;
    this.percentage = percentage;
  }

  public String getWord() {
    return word;
  }

  public int getPercentage() {
    return percentage;
  }
}
