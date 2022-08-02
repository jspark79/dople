package com.dople.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.dople.R;
import com.dople.util.SimpleStore;

public class DopleRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_register);

        initView();
    }

    private void initView() {
        ((ImageButton)findViewById(R.id.dolpe_register_back_btn)).setOnClickListener(this);
        ((Button)findViewById(R.id.dolpe_regiter_previous_btn)).setOnClickListener(this);
        ((Button)findViewById(R.id.dolpe_regiter_confirm_btn)).setOnClickListener(this);

        TextView kickname = (TextView) findViewById(R.id.dople_login_register_kickname);
        kickname.setText(SimpleStore.getString(this, "kickname"));

        Spinner spinner_sex = (Spinner) findViewById(R.id.dolpe_register_sex);
        Spinner spinner_age = (Spinner) findViewById(R.id.dolpe_register_age);


        ArrayAdapter<String> mSpinnerSexAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.sex));
        mSpinnerSexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(mSpinnerSexAdapter);

        ArrayAdapter<String> mSpinnerAgeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.age));
        mSpinnerAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(mSpinnerAgeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dolpe_register_back_btn:
            case R.id.dolpe_regiter_previous_btn:
                onBackPressed();
                break;

            case R.id.dolpe_regiter_confirm_btn:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("인증 완료")
                        .setMessage("운동 리더로 인증되었습니다.\n감사합니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        });
                dialog.show();
                break;
        }
    }
}