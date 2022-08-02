package com.dople.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dople.R;

public class DopleProgramActivity extends AppCompatActivity implements View.OnClickListener {
    private int program;
    LinearLayout mLine;
    LinearLayout mBottom;

    float line_y = 0.0f;
    float bottom_y = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        program = intent.getIntExtra("program", 1);

        if (program == 2) {
            setContentView(R.layout.activity_dople_program);
        } else {
            setContentView(R.layout.activity_dople_program2);
        }

        initView();

    }

    private void initView() {
        if (program == 2) {
            ImageButton mBackBtn1 = (ImageButton) findViewById(R.id.dople_program_back_btn);
            mBackBtn1.setOnClickListener(this);
            mBackBtn1.setColorFilter(Color.parseColor("#ffffff"));

            ((Button) findViewById(R.id.dople_program_request_chat)).setOnClickListener(this);

            mLine = (LinearLayout) findViewById(R.id.dople_program_line);
            mLine.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener1);

            mBottom = (LinearLayout) findViewById(R.id.dople_program_bottom);
            mBottom.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener2);

        } else {
            ImageButton mBackBtn2 = (ImageButton) findViewById(R.id.dople_program2_back_btn);
            mBackBtn2.setOnClickListener(this);
            mBackBtn2.setColorFilter(Color.parseColor("#ffffff"));

            ((Button) findViewById(R.id.dople_program2_request_chat)).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dople_program_back_btn:
            case R.id.dople_program2_back_btn:
                onBackPressed();
                break;
            case R.id.dople_program_request_chat:
            case R.id.dople_program2_request_chat:
                Intent intent = new Intent(DopleProgramActivity.this, DopleChatActivity.class);
                startActivity(intent);
                break;
        }
    }

    ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener1 = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Log.v("dople", "1111 line y : " + line_y);
            Log.v("dople", "1111 mBottom y : " + bottom_y);
            line_y = mLine.getY();


            removeOnGlobalLayoutListener(mLine.getViewTreeObserver(), mGlobalLayoutListener1);
        }
    };

    ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener2 = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Log.v("dople", "2222 line y : " + line_y);
            Log.v("dople", "2222 mBottom y : " + bottom_y);
            bottom_y = mBottom.getY();

            removeOnGlobalLayoutListener(mBottom.getViewTreeObserver(), mGlobalLayoutListener2);
        }
    };

    private static void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (observer == null)
            return ;
        observer.removeOnGlobalLayoutListener(listener);
    }
}