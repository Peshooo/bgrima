package com.bgrima.server.models;

public class WeightedRhyme implements Comparable<WeightedRhyme> {
  private String word;
  private long value;

  public WeightedRhyme(String word, long value) {
    this.word = word;
    this.value = value;
  }

  public String getWord() {
    return word;
  }

  public long getValue() {
    return value;
  }

  @Override
  public int compareTo(WeightedRhyme o) {
    if (value > o.value) {
      return -1;
    } else if (value < o.value) {
      return 1;
    } else {
      return 0;
    }
  }
}
