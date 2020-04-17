package com.bgrima.server.model;

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

  //Used in index.jsp to display everything beautifully
  public int getPercentage() {
    return percentage;
  }
}
