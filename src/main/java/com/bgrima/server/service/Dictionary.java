package com.bgrima.server.service;

import com.bgrima.server.model.Word;
import com.bgrima.server.model.WordKey;
import com.bgrima.server.service.utils.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.bgrima.server.service.Constants.*;

public class Dictionary {
    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);

    private static final Map<WordKey, List<String>> words = new HashMap<>();

    public static void loadWords() {
        logger.info("Proceeding to load words.");

        for (int syllablesCount = 1; syllablesCount <= 5; syllablesCount++) {
            if (SYLLABLES_COUNT_WITHOUT_VOWELS.contains(syllablesCount)) {
                loadWords(syllablesCount);
            } else {
                for (int vowelIndex = 0; vowelIndex < VOWELS_COUNT; vowelIndex++) {
                    loadWords(syllablesCount, vowelIndex);
                }
            }
        }

        logger.info("Successfully loaded all words. Size is {}", words.size());
    }

    private static void loadWords(int syllablesCount) {
        String filename = String.format("words/data_%d.txt", syllablesCount);
        loadWords(filename);
    }

    private static void loadWords(int syllablesCount, int vowelIndex) {
        String filename = String.format("words/data_%d_%d.txt", syllablesCount, vowelIndex);
        loadWords(filename);
    }

    private static void loadWords(String filename) {
        logger.info("Filename is {}", filename);
        File file = new File(Dictionary.class.getClassLoader().getResource(filename).getFile());
        logger.info("Opened file, np");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Word word = WordUtils.transform(line);
                WordKey wordKey = new WordKey(word);
                //logger.info("Key is {} {}", wordKey.getSyllablesCount(), wordKey.getLastVowel());

                words.putIfAbsent(wordKey, new ArrayList<>());
                words.get(wordKey).add(line);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found exception ", e);

            throw new RuntimeException(e);
        }
    }

    public static List<Word> getByKey(WordKey wordKey) {
        return Optional.ofNullable(words.get(wordKey))
                .orElse(Collections.emptyList())
                .stream()
                .map(WordUtils::transform)
                .collect(Collectors.toList());
    }
}
