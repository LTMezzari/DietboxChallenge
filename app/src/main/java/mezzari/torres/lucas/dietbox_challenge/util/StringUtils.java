package mezzari.torres.lucas.dietbox_challenge.util;

import android.text.SpannableStringBuilder;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class StringUtils {
    public static SpannableStringBuilder formatWord(String text, String word, Object format) {
        return formatWord(text, word, format, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static SpannableStringBuilder formatWord(SpannableStringBuilder text, String word, Object format) {
        return formatWord(text, word, format, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static SpannableStringBuilder formatWord(String text, String word, Object format, int flag) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        int start = text.indexOf(word);
        if (start < text.length() && start >= 0) {
            spannableStringBuilder.setSpan(format, start, start + word.length(), flag);
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder formatWord(SpannableStringBuilder text, String word, Object format, int flag) {
        int start = text.toString().indexOf(word);
        if (start < text.length() && start >= 0) {
            text.setSpan(format, start, start + word.length(), flag);
        }
        return text;
    }
}
