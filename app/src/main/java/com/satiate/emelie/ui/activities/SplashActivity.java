package com.satiate.emelie.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.satiate.emelie.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchHomeScreen();
    }

    private void launchHomeScreen()
    {
        Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
