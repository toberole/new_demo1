package com.zw.new_demo1.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zw.multi_process.service.CollectService;
import com.zw.multi_process.service.SaveService;
import com.zw.new_demo1.R;

public class MicroServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_service);

        findViewById(R.id.btn_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MicroServiceActivity.this, CollectService.class);
                startService(intent);
            }
        });

        findViewById(R.id.btn_comsume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MicroServiceActivity.this, SaveService.class);
                startService(intent);
            }
        });
    }
}