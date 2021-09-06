package mezzari.torres.lucas.dietbox_challenge.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Lucas T. Mezzari
 * @since 06/09/2021
 */
public final class DateUtils {
    public static int compareDifferenceInDays(Date startDate, Date endDate) {
        long diff =  endDate.getTime() - startDate.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static String formatDate(@NotNull Date date) {
        return formatDate(date, "yyyy-MM-dd", Locale.getDefault());
    }

    public static String formatDate(@NotNull Date date, @NotNull String format) {
        return formatDate(date, format, Locale.getDefault());
    }

    public static String formatDate(@NotNull Date date, @NotNull String format, @NotNull Locale locale) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format, locale).format(date);
    }
}
