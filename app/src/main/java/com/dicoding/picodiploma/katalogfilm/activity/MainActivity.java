package com.dicoding.picodiploma.katalogfilm.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dicoding.picodiploma.katalogfilm.R;
import com.dicoding.picodiploma.katalogfilm.fragment.FavoriteFragment;
import com.dicoding.picodiploma.katalogfilm.fragment.NowPlayingFragment;
import com.dicoding.picodiploma.katalogfilm.fragment.SearchFragment;
import com.dicoding.picodiploma.katalogfilm.fragment.UpcomingFragment;
import com.dicoding.picodiploma.katalogfilm.setting.AlarmReceiver;
import com.dicoding.picodiploma.katalogfilm.setting.SettingPreference;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;

    private AlarmReceiver alarmReceiver;

    FavoriteFragment favoriteFragment;
    NowPlayingFragment nowPlayingFragment;
    SearchFragment searchFragment;
    UpcomingFragment upcomingFragment;

    private int fragmentActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteFragment = new FavoriteFragment();
        nowPlayingFragment = new NowPlayingFragment();
        upcomingFragment = new UpcomingFragment();
        searchFragment = new SearchFragment();

        mFragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            if (fragment instanceof NowPlayingFragment) {
                nowPlayingFragment = (NowPlayingFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                fragmentActive = 1;
            }else if (fragment instanceof UpcomingFragment){
                upcomingFragment = (UpcomingFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                fragmentActive = 2;
            }else if (fragment instanceof SearchFragment){
                searchFragment = (SearchFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                fragmentActive = 3;
            }else if (fragment instanceof FavoriteFragment){
                favoriteFragment = (FavoriteFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                fragmentActive = 4;
            }
            updateFragment();
        } else {
           loadFragment(nowPlayingFragment);
        }
    }

    private void updateFragment(){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (fragmentActive) {
            case 1:
                mFragmentTransaction.replace(R.id.frame_layout, nowPlayingFragment, NowPlayingFragment.class.getSimpleName());
                break;
            case 2:
                mFragmentTransaction.replace(R.id.frame_layout, upcomingFragment, UpcomingFragment.class.getSimpleName());
                break;
            case 3:
                mFragmentTransaction.replace(R.id.frame_layout, searchFragment, SearchFragment.class.getSimpleName());
                break;
            case 4:
                mFragmentTransaction.replace(R.id.frame_layout, favoriteFragment, FavoriteFragment.class.getSimpleName());
                break;
        }
        mFragmentTransaction.commit();
    }

    private void setAlarmReceiver(){
        SettingPreference settingPreference = new SettingPreference(this);
        boolean dailyReminder = settingPreference.getDailyReminder();
        boolean releaseReminder = settingPreference.getReleaseReminder();
        if (dailyReminder){
            alarmReceiver.setDailyReminder();
        }
        if (releaseReminder){
            alarmReceiver.setReleaseTodayReminder();
        }
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.now_playing_menu:
                fragment = new NowPlayingFragment();
                fragmentActive = 1;
                break;
            case R.id.upcoming_menu:
                fragment = new UpcomingFragment();
                fragmentActive = 2;
                break;
            case R.id.favorite_menu:
                fragment = new FavoriteFragment();
                fragmentActive = 3;
                break;
            case R.id.search_menu:
                fragment = new SearchFragment();
                fragmentActive = 4;
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_reminder_settings){
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_refresh){
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    public void refresh(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}


