package com.stedi.originaltallycounter;

import android.content.SharedPreferences;

/**
 * Application settings holder (singleton)
 */
public class Settings {
    private static final String KEY_ALLOW_VIBRATION = "allow_vibration";
    private static final String KEY_ALLOW_ORIGINAL_CLICK_SOUND = "allow_original_click_sound";
    private static final String KEY_SELECTED_THEME = "selected_theme";

    private static Settings instance;

    private boolean allowVibration;
    private boolean allowOriginalClickSound;
    private Theme selectedTheme;

    public enum Theme {
        RED(R.style.RedTheme),
        ORANGE(R.style.OrangeTheme),
        BROWN(R.style.BrownTheme),
        GREEN(R.style.GreenTheme),
        BLUE(R.style.BlueTheme),
        PINK(R.style.PinkTheme);

        public static final Theme DEFAULT = Theme.BLUE;

        public int resId;

        private Theme(int resId) {
            this.resId = resId;
        }

        public static Theme find(String name) {
            for (Theme theme : Theme.values())
                if (theme.name().equals(name))
                    return theme;
            return DEFAULT;
        }
    }

    private Settings() {
        SharedPreferences sp = AppUtils.getSharedPreferences();
        allowVibration = sp.getBoolean(KEY_ALLOW_VIBRATION, true);
        allowOriginalClickSound = sp.getBoolean(KEY_ALLOW_ORIGINAL_CLICK_SOUND, true);
        selectedTheme = Theme.find(sp.getString(KEY_SELECTED_THEME, Theme.DEFAULT.name()));
    }

    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public boolean isAllowVibration() {
        return allowVibration;
    }

    public void setAllowVibration(final boolean allowVibration) {
        this.allowVibration = allowVibration;
        AppUtils.saveSharedPreferences(new AppUtils.PreferencesEditor() {
            @Override
            public void edit(SharedPreferences.Editor editor) {
                editor.putBoolean(KEY_ALLOW_VIBRATION, allowVibration);
            }
        });
    }

    public boolean isAllowOriginalClickSound() {
        return allowOriginalClickSound;
    }

    public void setAllowOriginalClickSound(final boolean allowOriginalClickSound) {
        this.allowOriginalClickSound = allowOriginalClickSound;
        AppUtils.saveSharedPreferences(new AppUtils.PreferencesEditor() {
            @Override
            public void edit(SharedPreferences.Editor editor) {
                editor.putBoolean(KEY_ALLOW_ORIGINAL_CLICK_SOUND, allowOriginalClickSound);
            }
        });
    }

    public Theme getSelectedTheme() {
        return selectedTheme;
    }

    public void setSelectedTheme(final Theme selectedTheme) {
        this.selectedTheme = selectedTheme;
        AppUtils.saveSharedPreferences(new AppUtils.PreferencesEditor() {
            @Override
            public void edit(SharedPreferences.Editor editor) {
                editor.putString(KEY_SELECTED_THEME, selectedTheme.name());
            }
        });
    }
}
