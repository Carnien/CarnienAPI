package net.carnien.api.util;

public class Formatter {

    private static final String[] COLOR_CODES = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "a", "b", "c", "d", "e", "f" };
    private static final String[] FORMAT_CODES = new String[] { "k", "l", "m", "n", "o", "r" };

    public static String format(FormatType type, String text) {
        String target = "";
        String replacement = "";

        switch (type) {
            case AND_TO_PARAGRAPH:
                target = "&";
                replacement = "§";
                break;

            case PARAGRAPH_TO_AND:
                target = "§";
                replacement = "&";
                break;

            case FILTER:
                text = format(FormatType.AND_TO_PARAGRAPH, text);

                for (String colorCode : COLOR_CODES) text = text.replace("§" + colorCode, "");
                for (String formatCode : FORMAT_CODES) text = text.replace("§" + formatCode, "");

                return text;
        }

        for (String colorCode : COLOR_CODES)
            text = text.replace(target + colorCode, replacement + colorCode);

        for (String formatCode : FORMAT_CODES)
            text = text.replace(target + formatCode, replacement + formatCode);

        return text;
    }

}
