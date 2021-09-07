package mezzari.torres.lucas.dietbox_challenge.model;

import junit.framework.TestCase;

import java.util.Date;

/**
 * @author Lucas T. Mezzari
 * @since 07/09/2021
 */
public final class ConvertersTest extends TestCase {

    // ---------------------------- fromTimestamp

    public void test_fromTimestamp_when_valid_time() {
        Date date = new Date();
        long timestamp = date.getTime();
        Date result = Converters.fromTimestamp(timestamp);
        assertNotNull(result);
        assertEquals(result, date);
    }

    public void test_fromTimestamp_when_null() {
        Long timestamp = null;
        Date result = Converters.fromTimestamp(timestamp);
        assertNull(result);
    }

    public void test_fromTimestamp_when_invalid_time() {
        long timestamp = 21321;
        Date date = new Date(timestamp);
        Date result = Converters.fromTimestamp(timestamp);
        assertNotNull(result);
        assertEquals(result, date);
    }

    // ---------------------------- dateToTimestamp

    public void test_dateToTimestamp_when_valid_time() {
        Date date = new Date();
        Long timestamp = date.getTime();
        Long result = Converters.dateToTimestamp(date);
        assertNotNull(result);
        assertEquals(result, timestamp);
    }

    public void test_dateToTimestamp_when_null() {
        Date timestamp = null;
        Long result = Converters.dateToTimestamp(timestamp);
        assertNull(result);
    }

    public void test_dateToTimestamp_when_invalid_time() {
        Long timestamp = 21321L;
        Date date = new Date(timestamp);
        Long result = Converters.dateToTimestamp(date);
        assertNotNull(result);
        assertEquals(result, timestamp);
    }
}