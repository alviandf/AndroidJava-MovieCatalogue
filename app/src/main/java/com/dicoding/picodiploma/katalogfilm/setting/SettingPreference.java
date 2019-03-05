package com.dicoding.picodiploma.katalogfilm.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingPreference {
    SharedPreferences preference;
    Context context;

    private String KEY_DAILY_REMINDER = "daily_reminder";
    private String KEY_RELEASE_REMINDER = "release_reminder";

    public SettingPreference(Context context) {
        preference = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setDailyReminder(Boolean input) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(KEY_DAILY_REMINDER, input);
        editor.apply();
    }

    public Boolean getDailyReminder() {
        return preference.getBoolean(KEY_DAILY_REMINDER, true);
    }

    public void setReleaseReminder(Boolean input) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(KEY_RELEASE_REMINDER, input);
        editor.apply();
    }

    public Boolean getReleaseReminder() {
        return preference.getBoolean(KEY_RELEASE_REMINDER, true);
    }
}
