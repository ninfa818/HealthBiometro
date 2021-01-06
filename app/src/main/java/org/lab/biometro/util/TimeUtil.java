package org.lab.biometro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_MMMnDD = "MM.dd";

    static public Date getDateFromString(String formatter, String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    static public String getStringFromDate(String formatter, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
        return format.format(date);
    }

}
