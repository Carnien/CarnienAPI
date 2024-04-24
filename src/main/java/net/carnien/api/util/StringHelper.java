package net.carnien.api.util;

public class StringHelper {

    public static String toSentence(String[] words) {
        if (words == null) return null;
        if (words.length == 1) return words[0];

        StringBuilder sentence = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; i++) {
            final String word = words[i];
            sentence.append(" ");
            sentence.append(word);
        }

        return sentence.toString();
    }

}
