package com.dople.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dople.R;

public class DopleSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_search);

        initView();

        Intent intent = getIntent();
        mSearchText.setText(intent.getStringExtra("key"));
    }

    private void initView() {
        mSearchText = (EditText) findViewById(R.id.dolpe_search_search_text);

        ImageButton mBackBtn = (ImageButton) findViewById(R.id.dople_search_back_btn);
        mBackBtn.setOnClickListener(this);

        LinearLayout mProgram2 = (LinearLayout) findViewById(R.id.dople_search_program_2);
        mProgram2.setOnClickListener(this);

        LinearLayout mProgram1 = (LinearLayout) findViewById(R.id.dople_search_program_1);
        mProgram1.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dople_search_back_btn:
                onBackPressed();
                break;

            case R.id.dople_search_program_1:
                Intent intent1 = new Intent(DopleSearchActivity.this, DopleProgramActivity.class);
                intent1.putExtra("program", 1);
                startActivity(intent1);
                break;

            case R.id.dople_search_program_2:
                Intent intent2 = new Intent(DopleSearchActivity.this, DopleProgramActivity.class);
                intent2.putExtra("program", 2);
                startActivity(intent2);
                break;
        }
    }
}