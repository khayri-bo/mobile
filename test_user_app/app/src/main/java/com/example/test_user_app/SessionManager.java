package com.example.test_user_app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SessionManager {
    private MutableLiveData<Boolean> userLoggedIn = new MutableLiveData<>();

    public LiveData<Boolean> isUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        userLoggedIn.setValue(loggedIn);
    }
}