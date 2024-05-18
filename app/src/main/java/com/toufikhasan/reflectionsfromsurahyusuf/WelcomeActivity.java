package com.toufikhasan.reflectionsfromsurahyusuf;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        Thread thread = new Thread(this::PressingApplication);
        thread.start();
    }

    public void PressingApplication() {
        int progress;
        for (progress = 0; progress <= 100; progress = progress + 1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        openApplication();
    }

    public void openApplication() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }
}