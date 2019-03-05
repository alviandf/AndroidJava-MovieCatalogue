package com.dicoding.picodiploma.katalogfilm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dicoding.picodiploma.katalogfilm.R;
import com.dicoding.picodiploma.katalogfilm.setting.AlarmReceiver;
import com.dicoding.picodiploma.katalogfilm.setting.SettingPreference;

public class SettingActivity extends AppCompatActivity {

    SettingPreference settingPreference;
    private AlarmReceiver alarmReceiver;


    private boolean isDailyReminder;
    private boolean isReleaseReminder;

    Switch swtDailyReminder;
    Switch swtReleaseReminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //alarmReceiver = new AlarmReceiver(this);

        settingPreference = new SettingPreference(this);
        swtDailyReminder = findViewById(R.id.switch_daily_reminder);
        swtReleaseReminder = findViewById(R.id.switch_release_reminder);

        isDailyReminder = settingPreference.getDailyReminder();
        isReleaseReminder = settingPreference.getReleaseReminder();

        swtDailyReminder.setChecked(isDailyReminder);
        swtReleaseReminder.setChecked(isReleaseReminder);

        alarmReceiver = new AlarmReceiver(this);

        swtDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDailyReminder = isChecked;
                settingPreference.setDailyReminder(isDailyReminder);
                if(isDailyReminder) {
                    alarmReceiver.setDailyReminder();
                } else {
                    alarmReceiver.cancelDailyReminder(SettingActivity.this);
                }
            }
        });

        swtReleaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isReleaseReminder = isChecked;
                settingPreference.setReleaseReminder(isReleaseReminder);
                if(isReleaseReminder) {
                    alarmReceiver.setReleaseTodayReminder();
                }else{
                    alarmReceiver.cancelReleaseToday(SettingActivity.this);
                }
            }
        });
    }
}
