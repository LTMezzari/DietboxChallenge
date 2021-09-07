package mezzari.torres.lucas.dietbox_challenge.model;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public final class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value != null ? new Date(value) : null;
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date != null ? date.getTime() : null;
    }
}
