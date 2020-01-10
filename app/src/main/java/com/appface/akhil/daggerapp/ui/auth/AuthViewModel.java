package com.appface.akhil.daggerapp.ui.auth;

import android.util.Log;

import com.appface.akhil.daggerapp.SessionManager;
import com.appface.akhil.daggerapp.models.User;
import com.appface.akhil.daggerapp.network.auth.AuthApi;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private final AuthApi authApi;

    private static final String TAG = "AuthViewModel";

//    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();  MOving to session manager
    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "AuthViewModel: viewmodel is working ");
    }

    public void authenticateWithId(int userId) {

//        authUser.setValue(AuthResource.loading((User) null));
//        final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(
//                authApi.getUser(userId)
//                .subscribeOn(Schedulers.io())
//                .onErrorReturn(new Function<Throwable, User>() {
//                    @Override
//                    public User apply(Throwable throwable) throws Exception {
//                        User errorUser = new User();
//                        errorUser.setId(-1);
//                        return errorUser;
//                    }
//                })
//                .map(new Function<User, AuthResource<User>>() {
//                    @Override
//                    public AuthResource<User> apply(User user) throws Exception {
//                        if(user.getId() == -1) {
//                            return AuthResource.error("Could not authenticate", (User) null);
//                        }
//                        return AuthResource.authenticated(user);
//                    }
//                })
//        );
        Log.d(TAG, "authenticateWithId: attempting to login");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if(user.getId() == -1) {
                                    return AuthResource.error("Could not authenticate", (User) null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
        );
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }

//    public LiveData<AuthResource<User>> observeUser() {
//        return authUser;
//    }
}
