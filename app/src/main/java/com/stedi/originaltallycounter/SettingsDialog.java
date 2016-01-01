package com.stedi.originaltallycounter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private SwitchCompat switchVibration;
    private SwitchCompat switchClickSound;
    private Spinner spinnerThemes;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View layout = LayoutInflater.from(builder.getContext()).inflate(R.layout.settings_dialog, null);

        switchVibration = (SwitchCompat) layout.findViewById(R.id.settings_dialog_switch_vibration);
        switchVibration.setChecked(Settings.getInstance().isAllowVibration());

        switchClickSound = (SwitchCompat) layout.findViewById(R.id.settings_dialog_switch_click_sound);
        switchClickSound.setChecked(Settings.getInstance().isAllowOriginalClickSound());

        spinnerThemes = (Spinner) layout.findViewById(R.id.settings_dialog_spinner_themes);
        String[] themesNames = new String[Settings.Theme.values().length];
        for (Settings.Theme theme : Settings.Theme.values())
            themesNames[theme.ordinal()] = theme.name();
        spinnerThemes.setAdapter(new ArrayAdapter<>(builder.getContext(), android.R.layout.simple_spinner_dropdown_item, themesNames));
        spinnerThemes.setSelection(Settings.getInstance().getSelectedTheme().ordinal());

        builder.setTitle(R.string.settings);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setView(layout);
        return builder.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Settings settings = Settings.getInstance();

        boolean allowVibration = switchVibration.isChecked();
        if (settings.isAllowVibration() != allowVibration)
            settings.setAllowVibration(allowVibration);

        boolean allowClickSound = switchClickSound.isChecked();
        if (settings.isAllowOriginalClickSound() != allowClickSound)
            settings.setAllowOriginalClickSound(allowClickSound);

        Settings.Theme selectedTheme = Settings.Theme.find(spinnerThemes.getSelectedItem().toString());
        if (settings.getSelectedTheme() != selectedTheme) {
            settings.setSelectedTheme(selectedTheme);
            Activity activity = getActivity();
            dismiss();
            activity.finish();
            activity.startActivity(new Intent(activity, activity.getClass()));
        }
    }
}
