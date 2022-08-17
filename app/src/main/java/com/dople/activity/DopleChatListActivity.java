package com.dople.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dople.R;

public class DopleChatListActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_chat_list);

        initView();
    }

    private void initView() {
        ImageButton backBtn = (ImageButton)findViewById(R.id.dople_chat_list_back_btn);
        backBtn.setColorFilter(Color.parseColor("#ffffff"));
        backBtn.setOnClickListener(this);

        ((RelativeLayout)findViewById(R.id.dople_chat_list_chat_item)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dople_chat_list_back_btn:
                onBackPressed();
                break;
            case R.id.dople_chat_list_chat_item:
                Intent intent = new Intent(DopleChatListActivity.this, DopleChatActivity.class);
                startActivity(intent);
                break;
        }
    }
}