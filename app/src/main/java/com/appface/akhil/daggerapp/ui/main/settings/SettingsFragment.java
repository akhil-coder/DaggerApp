package com.appface.akhil.daggerapp.ui.main.settings;


import android.os.Bundle;

import com.appface.akhil.daggerapp.R;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.settings_fragment_preferences, rootKey);
    }
}
