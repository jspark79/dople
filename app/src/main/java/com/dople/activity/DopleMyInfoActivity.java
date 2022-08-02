package com.dople.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dople.R;

public class DopleMyInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_my_info);

        initView();
    }

    private void initView() {
        ((RelativeLayout) findViewById(R.id.dolpe_myinfo_register)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.dolpe_myinfo_chat)).setOnClickListener(this);

        ImageButton backBtn = (ImageButton) findViewById(R.id.dople_myinfo_back_btn);


        backBtn.setColorFilter(Color.parseColor("#ffffff"));
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.dolpe_myinfo_chat:
                intent = new Intent(DopleMyInfoActivity.this, DopleChatActivity.class);
                startActivity(intent);
                break;
            case R.id.dople_myinfo_back_btn:
                onBackPressed();
                break;
            case R.id.dolpe_myinfo_register:
                intent = new Intent(DopleMyInfoActivity.this, DopleRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}