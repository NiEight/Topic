package com.example.topic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timeThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000); // 3초
                } catch(InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //intent를 통한 일방향 통신
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timeThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (false) {
            super.onBackPressed();
        } else {
        }
    }
}
