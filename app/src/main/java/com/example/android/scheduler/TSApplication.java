package com.example.android.scheduler;

import android.app.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by SathishSr
 */

public class TSApplication extends Application {

    private String currentSchedule ="";
    @Override
    public void onCreate() {
        super.onCreate();

        long date = System.currentTimeMillis();

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        String dateString = sdf.format(date);

        sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 120);  // number of days to add
        dateString = sdf.format(c.getTime());

        currentSchedule = dateString;
    }

    public String getCurrentSchedule() {
        return currentSchedule;
    }
}
