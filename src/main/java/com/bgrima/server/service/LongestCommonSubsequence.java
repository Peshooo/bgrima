package com.bgrima.server.service;

import com.bgrima.server.service.utils.PhonemeUtils;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubsequence {
    public static long longestCommonSubsequence(String a, String b) {
        List<Integer> phonemesA = toPhonemesList(a);
        List<Integer> phonemesB = toPhonemesList(b);

        return longestCommonSubsequence(phonemesA, phonemesB);
    }

    private static List<Integer> toPhonemesList(String a) {
        List<Integer> phonemes = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            phonemes.add(PhonemeUtils.getPhonemeGroup(a.charAt(i)));
        }

        return phonemes;
    }

    private static long longestCommonSubsequence(List<Integer> a, List<Integer> b) {
        int[][] lcs = new int[a.size()][b.size()];

        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                Integer elementA = a.get(i);
                Integer elementB = b.get(j);

                if (elementA.equals(elementB)) {
                    lcs[i][j] = 1;

                    if (i > 0 && j > 0) {
                        lcs[i][j] += lcs[i - 1][j - 1];
                    }
                } else {
                    lcs[i][j] = 0;

                    if (i > 0) {
                        lcs[i][j] = Math.max(lcs[i][j], lcs[i - 1][j]);
                    }
                    if (j > 0) {
                        lcs[i][j] = Math.max(lcs[i][j], lcs[i][j - 1]);
                    }
                }
            }
        }

        return lcs[a.size() - 1][b.size() - 1];
    }
}
