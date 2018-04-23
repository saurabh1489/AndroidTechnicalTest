package com.bridge.androidtechnicaltest.di;

import com.bridge.androidtechnicaltest.ui.MainActivity;

import dagger.Component;

@Component(modules = {
        NetworkModule.class,
        DatabaseModule.class,
})
public abstract class ApplicationComponent {
    public abstract void inject(MainActivity mainActivity);
}
