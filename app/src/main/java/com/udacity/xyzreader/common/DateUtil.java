package com.udacity.xyzreader.common;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    private static final String TAG = "DateUtil";

    private DateUtil() {}

    public static String format(String source) {
        SimpleDateFormat originDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMM dd, yyyy");

        String parsedDate = null;

        try {
            Date date = originDateFormat.parse(source);
            parsedDate = outputDateFormat.format(date);

            if (parsedDate.contains("01,"))
                parsedDate = parsedDate.replace("01", "1");

        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        return parsedDate;
    }
}
