package com.dople.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dople.R;
import com.dople.util.Config;
import com.dople.util.SimpleStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DopleMainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;

    private Button mLoginBtn;

    private EditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initView() {
        mLoginBtn = (Button) findViewById(R.id.dople_main_login_btn);
        mLoginBtn.setOnClickListener(this);

        mEmail = (EditText) findViewById(R.id.dople_main_email);
        mPassword = (EditText) findViewById(R.id.dople_main_password);

        ((TextView) findViewById(R.id.dople_main_register)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dople_main_register:
                Intent intent = new Intent(DopleMainActivity.this, DopleLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.dople_main_login_btn:
                login();
                break;
        }

    }

    private void login() {
        if (mEmail.getText().toString().isEmpty()) {
            Toast.makeText(DopleMainActivity.this, "email empty", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (mPassword.getText().toString().isEmpty()) {
            Toast.makeText(DopleMainActivity.this, "mPassword empty", Toast.LENGTH_SHORT).show();
            return ;
        }
        firebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        } catch(FirebaseAuthWeakPasswordException e) {
                                            Toast.makeText(DopleMainActivity.this,"login 실패." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"비밀번호가 간단해요.." ,Toast.LENGTH_SHORT).show();
                                        } catch(FirebaseAuthInvalidCredentialsException e) {
                                            Toast.makeText(DopleMainActivity.this,"login 실패." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"email 형식에 맞지 않습니다." ,Toast.LENGTH_SHORT).show();
                                        } catch(FirebaseAuthUserCollisionException e) {
                                            Toast.makeText(DopleMainActivity.this,"login 실패." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"이미존재하는 email 입니다." ,Toast.LENGTH_SHORT).show();
                                        } catch(Exception e) {
                                            Toast.makeText(DopleMainActivity.this,"login 실패." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"다시 확인해주세요.." ,Toast.LENGTH_SHORT).show();
                                        }
                                        Log.v("dople", "task. fail()");
                                    } else {

                                        Log.v("dople", "task. success()");
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user.isEmailVerified()) {
                                            SimpleStore.saveString(DopleMainActivity.this, Config.KICK_NAME, user.getDisplayName());
                                            SimpleStore.saveString(DopleMainActivity.this, Config.USER_EMAIL, user.getEmail());
//                                            SimpleStore.saveString(DopleMainActivity.this, Config.USER_NAME, user.getDisplayName());
                                            Intent intent = new Intent(DopleMainActivity.this, DopleHomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(DopleMainActivity.this,"email 확인 먼저" ,Toast.LENGTH_SHORT).show();

                                        }
                                    }
                            }
                        });
    }
}