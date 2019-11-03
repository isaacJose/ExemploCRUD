package com.example.exemplocrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler =new Handler();
        handler.postDelayed(this, Integer.parseInt(getString(R.string.temp_splash)));
    }

    @Override
    public void run() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
