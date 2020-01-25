package com.appface.akhil.daggerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.appface.akhil.daggerapp.models.User;
import com.appface.akhil.daggerapp.ui.auth.AuthActivity;
import com.appface.akhil.daggerapp.ui.auth.AuthResource;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING: {
                            break;
                        }
                        case AUTHENTICATED: {
                            Log.d(TAG, "onChanged: LOGIN SUCCESS" + userAuthResource.data.getEmail());
                            break;
                        }
                        case ERROR: {
                            Log.e(TAG, "onChanged: " + userAuthResource.message);
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            navLoginScreen();
                            break;
                        }

                    }
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    protected void storeSharedPreferences(int sec) {
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putInt("time", sec);
        myEdit.commit();
    }

    protected int loadSharedPreferences(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String dropdown = sharedPreferences.getString("dropdown", "2");
        return Integer.parseInt(dropdown);
    }

}
