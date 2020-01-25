package com.appface.akhil.daggerapp.ui.main.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appface.akhil.daggerapp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();
    }
}
