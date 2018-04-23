package com.bridge.androidtechnicaltest.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bridge.androidtechnicaltest.App;
import com.bridge.androidtechnicaltest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.container, new PupilListFragment())
                    .commit();
        }
    }
}
