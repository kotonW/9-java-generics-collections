package com.example.task03;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

public class Task03Main {

    private static final String DICTIONARY = "/singular.txt";

    private static final Charset DICTIONARY_CHARSET = Charset.forName("windows-1251");

    public static void main(String[] args) throws IOException {

        try (InputStream dictionary = Task03Main.class.getResourceAsStream(DICTIONARY)) {
            List<Set<String>> anagrams = findAnagrams(dictionary, DICTIONARY_CHARSET);
            for (Set<String> anagram : anagrams) {
                System.out.println(anagram);
            }
        }

    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        Set<String> words = new TreeSet<>();
        try (var scanner = new Scanner(inputStream, charset)) {
            while (scanner.hasNext()) {
                var word = scanner.next().toLowerCase();
                if (word.length() >= 3 && word.matches("[а-яё]+")) {
                    words.add(word);
                }
            }
        }

        Map<String, Set<String>> groups = new HashMap<>();
        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            groups.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
        }

        List<Set<String>> result = new ArrayList<>();
        for (Set<String> group : groups.values()) {
            if (group.size() >= 2) {
                result.add(group);
            }
        }
        result.sort(Comparator.comparing(set -> set.iterator().next()));
        return result;
    }
}
