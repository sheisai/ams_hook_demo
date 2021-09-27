package com.antonio.ams_hook_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 测试主类
 *
 * @author antonio
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_ams_hook).setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        AmsHookHelper.hookActivityManager();
        super.attachBaseContext(newBase);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}