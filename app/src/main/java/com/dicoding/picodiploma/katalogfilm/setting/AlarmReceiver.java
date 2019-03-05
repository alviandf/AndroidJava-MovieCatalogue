package com.dicoding.picodiploma.katalogfilm.setting;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dicoding.picodiploma.katalogfilm.BuildConfig;
import com.dicoding.picodiploma.katalogfilm.R;
import com.dicoding.picodiploma.katalogfilm.model.Movie;
import com.dicoding.picodiploma.katalogfilm.model.MoviesResponse;
import com.dicoding.picodiploma.katalogfilm.rest.ApiClient;
import com.dicoding.picodiploma.katalogfilm.rest.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReceiver extends BroadcastReceiver {

    private final static String API_KEY = BuildConfig.TMDB_API_KEY;;

    private final int ID_DAILY_REMINDER = 100;
    private final int ID_RELEASE_TODAY = 101;

    public static final String TYPE_DAILY = "daily";
    public static final String TYPE_RELEASE = "release";

    public static final String EXTRA_TYPE = "type";

    private Context context;

    public AlarmReceiver() {
    }

    public AlarmReceiver(Context context) {
        this.context = context;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if(type.equals(TYPE_DAILY)){
            showDailyReminder(context);
        }else if (type.equals(TYPE_RELEASE)){
            getReleaseToday(context);
        }
    }

    private void getReleaseToday(final Context context){
        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);
        Call<MoviesResponse> call = apiService.getNowPlayingMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()){
                    List<Movie> movies = response.body().getResults();
                    String DATE_FORMAT = "yyyy-MM-dd";
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
                    for (Movie movie: movies) {
                        if (movie.getReleaseDate().equals(simpleDateFormat.format(calendar.getTime()))){
                            showReleaseToday(context, movie.getTitle(), movie.getTitle()+ R.string.notif_release);
                        }
                    }
                }else {
                    Log.e("Wadaw", "error");
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e("Wadaw", t.toString());
            }
        });
    }

    private Calendar getReminderTime(String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, type.equals(TYPE_DAILY) ? 7 : 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private Intent getReminderIntent(String type) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    public void setDailyReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY).getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }

    public void setReleaseTodayReminder() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, getReminderIntent(TYPE_RELEASE), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void showDailyReminder(Context context) {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder channel";

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_white_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.daily_reminder_content_text))
                .setSubText(context.getResources().getString(R.string.app_name))
                .setAutoCancel(true);

        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void showReleaseToday(Context context,String title,String text) {
        int NOTIFICATION_ID = 2;
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Today release channel";

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_white_24dp))
                .setContentTitle(title)
                .setContentText(text)
                .setSubText(context.getResources().getString(R.string.app_name))
                .setAutoCancel(true);

        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    public void cancelDailyReminder(Context context){
        cancelAlarm(context, TYPE_DAILY);
    }

    public void cancelReleaseToday(Context context){
        cancelAlarm(context, TYPE_RELEASE);
    }

    private void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_REMINDER: ID_RELEASE_TODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
