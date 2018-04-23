package com.bridge.androidtechnicaltest;

import android.app.Application;

import com.bridge.androidtechnicaltest.di.ApplicationComponent;
import com.bridge.androidtechnicaltest.di.DaggerApplicationComponent;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.create();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
