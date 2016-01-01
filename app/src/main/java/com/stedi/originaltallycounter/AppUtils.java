package com.stedi.originaltallycounter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Static methods holder / helper class
 */
public class AppUtils extends Application {
    private static AppUtils instance;

    public interface PreferencesEditor {
        void edit(SharedPreferences.Editor editor);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    public static void saveSharedPreferences(PreferencesEditor editor) {
        SharedPreferences.Editor spEditor = getSharedPreferences().edit();
        editor.edit(spEditor);
        spEditor.apply();
    }
}
