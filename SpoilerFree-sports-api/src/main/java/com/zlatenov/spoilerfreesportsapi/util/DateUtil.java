package com.zlatenov.spoilerfreesportsapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author Angel Zlatenov
 */

public class DateUtil {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getCurrentDateWithoutTime() {
        Date today = null;
        try {
            today = DATE_FORMAT.parse(DATE_FORMAT.format(Date.from(Instant.now())));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }

    public static Date parseDate(String date) {
        Date result = null;
        try {
            result = DATE_FORMAT.parse(DATE_FORMAT.format(date));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
