package com.stedi.originaltallycounter;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_COUNT_VALUE = "count_value";
    private static final int VIBRATE_LENGTH = 125;

    private CounterView counter;
    private Vibrator vibrator;
    private SoundPool soundPool;
    private int originalClickSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Settings.getInstance().getSelectedTheme().resId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.main_activity_root).setOnClickListener(this);
        counter = (CounterView) findViewById(R.id.main_activity_counter);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
        originalClickSoundId = soundPool.load(this, R.raw.original_click_sound, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        counter.setCount(AppUtils.getSharedPreferences().getInt(KEY_COUNT_VALUE, 0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppUtils.saveSharedPreferences(new AppUtils.PreferencesEditor() {
            @Override
            public void edit(SharedPreferences.Editor editor) {
                editor.putInt(KEY_COUNT_VALUE, counter.getCount());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_activity_menu_reset) {
            counter.reset();
        } else if (item.getItemId() == R.id.main_activity_menu_settings) {
            new SettingsDialog().show(getSupportFragmentManager(), SettingsDialog.class.getName());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (Settings.getInstance().isAllowVibration())
            vibrator.vibrate(VIBRATE_LENGTH);
        if (Settings.getInstance().isAllowOriginalClickSound())
            soundPool.play(originalClickSoundId, 1, 1, 1, 0, 1);
        counter.countUp();
    }
}
