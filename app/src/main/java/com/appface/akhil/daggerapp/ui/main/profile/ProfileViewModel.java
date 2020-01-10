package com.appface.akhil.daggerapp.ui.main.profile;

import android.util.Log;

import com.appface.akhil.daggerapp.SessionManager;
import com.appface.akhil.daggerapp.models.User;
import com.appface.akhil.daggerapp.ui.auth.AuthResource;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: VM is ready");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }
}
