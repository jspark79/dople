package com.dople.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dople.R;
import com.dople.data.FirebasePost;
import com.dople.util.Config;
import com.dople.util.SimpleStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DopleRegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mName, mLocation;
    private Spinner mGender, mAge;

    private DatabaseReference mDBReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_register);

        mDBReference = FirebaseDatabase.getInstance().getReference();

        initView();

        getDataFromFireBase();

    }

    private void initView() {
        ((ImageButton)findViewById(R.id.dolpe_register_back_btn)).setOnClickListener(this);
        ((Button)findViewById(R.id.dolpe_regiter_previous_btn)).setOnClickListener(this);
        ((Button)findViewById(R.id.dolpe_regiter_confirm_btn)).setOnClickListener(this);

        mName = (EditText) findViewById(R.id.dople_login_register_kickname);
        mLocation = (EditText) findViewById(R.id.dople_login_register_location);

        mGender = (Spinner) findViewById(R.id.dolpe_register_gender);
        mAge = (Spinner) findViewById(R.id.dolpe_register_age);

        ArrayAdapter<String> mSpinnerSexAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.gender));
        mSpinnerSexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGender.setAdapter(mSpinnerSexAdapter);

        ArrayAdapter<String> mSpinnerAgeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.age));
        mSpinnerAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAge.setAdapter(mSpinnerAgeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dolpe_register_back_btn:
            case R.id.dolpe_regiter_previous_btn:
                onBackPressed();
                break;

            case R.id.dolpe_regiter_confirm_btn:
                if (checkInputFiled()) {
                    checkUsefulKickName();
                }
                break;
        }
    }

    private boolean checkInputFiled() {
        Log.v("dople", "uploadRegisterData name : " + mName.getText().toString());
        Log.v("dople", "uploadRegisterData location : " + mLocation.getText().toString());
        Log.v("dople", "uploadRegisterData age : " + mGender.getSelectedItemPosition());
        Log.v("dople", "uploadRegisterData gender : " + mAge.getSelectedItemPosition());

        if (mName.getText().toString().isEmpty()) {
            Toast.makeText(DopleRegisterActivity.this, "name is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mLocation.getText().toString().isEmpty()) {
            Toast.makeText(DopleRegisterActivity.this, "location is empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void uploadRegisterData() {
        FirebasePost post = new FirebasePost(SimpleStore.getString(this, Config.USER_EMAIL),
                mName.getText().toString(),
                mLocation.getText().toString(),
                mAge.getSelectedItemPosition(),
                mGender.getSelectedItemPosition());

        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/" + Config.FIREBASE_PROFILE_DIR + "/" + SimpleStore.getString(this, Config.USER_UID),
                postValues);
        mDBReference.updateChildren(childUpdates);

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
    }

    private void getDataFromFireBase() {

        Log.v("dople", "addValueEventListener" );

        mDBReference.child(Config.FIREBASE_PROFILE_DIR).child(SimpleStore.getString(this, Config.USER_UID))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) {
                            Log.v("dople", "addValueEventListener snapshot.getValue() null");
                            return;
                        }
                        Log.v("dople", "addValueEventListener onDataChange : " + snapshot.getValue().toString());
                        FirebasePost data = snapshot.getValue(FirebasePost.class);

                        mName.setText(data.name);
                        mLocation.setText(data.location);
                        mGender.setSelection(data.gender);
                        mAge.setSelection(data.age);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.v("dople", "addValueEventListener onCancelled : " + error.toString());
                    }
                });
    }

    private void checkUsefulKickName() {

        Log.v("dople", "checkUsefulKickName" );
        uploadRegisterData();
//        mDBReference.child(Config.FIREBASE_PROFILE_DIR)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.getValue() == null) {
//                            Log.v("dople", "checkUsefulKickName snapshot.getValue() null");
//                            uploadRegisterData();
//                            return;
//                        }
//
//                        Log.v("dople", "checkUsefulKickName onDataChange : " + snapshot.getValue().toString());
//
//                        for(DataSnapshot ds : snapshot.getChildren())           //여러 값을 불러와 하나씩
//                        {
//                            Log.v("dople", "checkUsefulKickName ds : " + ds.getValue().toString());
//                            FirebasePost data = ds.getValue(FirebasePost.class);
//
//                            if (data.name.equals(mName.getText().toString())) {
//                                Log.v("dople", "checkUsefulKickName ds data.name : " + data.name);
//                                Log.v("dople", "checkUsefulKickName mName : " + mName.getText().toString());
//                                Toast.makeText(DopleRegisterActivity.this, "has same name. use different", Toast.LENGTH_SHORT).show();
//                                return ;
//                            }
//                        }
//                        uploadRegisterData();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Log.v("dople", "checkUsefulKickName onCancelled : " + error.toString());
//                    }
//                });
    }
}