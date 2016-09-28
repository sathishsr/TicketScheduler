package com.example.android.scheduler;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SampleSchedulingService extends IntentService {
    public SampleSchedulingService() {
        super("SchedulingService");
    }

    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;


    private HashMap<String, String> mapValues = new HashMap<>();


    private void setMapValues() {

        if (mapValues != null) {

            mapValues.put("2017-01-16", "Pongal");
            mapValues.put("2017-01-25", "Republic day");
            mapValues.put("2017-01-29", "Republic day return");

            mapValues.put("2017-04-13", "Tamil new year");
            mapValues.put("2017-04-16", "Tamil new year return");
            mapValues.put("2017-04-28", "Workers day");

            mapValues.put("2017-05-01", "Workers day return");

            mapValues.put("2017-06-23", "Ramzan");
            mapValues.put("2017-06-26", "Ramzan return");

            mapValues.put("2017-07-11", "Independence day");
            mapValues.put("2017-07-15", "Independence day return");
            mapValues.put("2017-07-24", "Ganesh chaturthi");
            mapValues.put("2017-07-27", "Ganesh chaturthi return");

            mapValues.put("2017-08-01", "Onam");
            mapValues.put("2017-08-05", "Onam return");

            mapValues.put("2017-09-29", "Gandhi Jayanthi");

            mapValues.put("2017-10-02", "Gandhi jayanthi return");
            mapValues.put("2017-10-17", "Deepavali");
            mapValues.put("2017-10-22", "Deepavali return");

            mapValues.put("2017-12-22", "Christmas");
            mapValues.put("2017-12-25", "Christmas return");

        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)

        setMapValues();

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

        System.out.println("DATE:::" + dateString);

        if (mapValues.containsKey(dateString)) {
            sendNotification(mapValues.get(dateString));
        }


        // Release the wake lock provided by the BroadcastReceiver.
        SampleAlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_train_white_24dp)
                        .setContentTitle(getString(R.string.doodle_alert))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

//
// The methods below this line fetch content from the specified URL and return the
// content as a string.
//

