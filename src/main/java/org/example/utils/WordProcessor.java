package org.example.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordProcessor {

  public static void countWords(Map<String, Integer> wordCount, String translated) {
    for (String word : translated.split(" ")) {
      word = word.toLowerCase().replaceAll("[^a-z]", "");
      wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
    }
  }

  public static void printRepeatedWords(Map<String, Integer> wordCount) {
    System.out.println("\nRepeated Words:");
    wordCount.entrySet().stream()
        .filter(entry -> entry.getValue() > 2)
        .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
  }

  public static void translateTitles(List<String> titles) throws IOException {
    Map<String, Integer> wordCount = new HashMap<>();
    System.out.println("\nTranslated Titles:");
    for (String title : titles) {
      String translated = TranslationService.translateText(title);
      System.out.println(translated);
      countWords(wordCount, translated);
    }
    printRepeatedWords(wordCount);
  }
}
