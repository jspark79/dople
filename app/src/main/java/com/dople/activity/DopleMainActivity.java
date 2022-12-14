package com.dople.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dople.R;
import com.dople.util.Config;
import com.dople.util.SimpleStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DopleMainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;

    private Button mLoginBtn;

    private EditText mEmail, mPassword;

    @Override
    protected void onResume() {
        super.onResume();
        mEmail.setText("");
        mPassword.setText("");
    }

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
                                            Toast.makeText(DopleMainActivity.this,"login ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"??????????????? ????????????.." ,Toast.LENGTH_SHORT).show();
                                        } catch(FirebaseAuthInvalidCredentialsException e) {
                                            Toast.makeText(DopleMainActivity.this,"login ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"email ????????? ?????? ????????????." ,Toast.LENGTH_SHORT).show();
                                        } catch(FirebaseAuthUserCollisionException e) {
                                            Toast.makeText(DopleMainActivity.this,"login ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"?????????????????? email ?????????." ,Toast.LENGTH_SHORT).show();
                                        } catch(Exception e) {
                                            Toast.makeText(DopleMainActivity.this,"login ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"?????? ??????????????????.." ,Toast.LENGTH_SHORT).show();
                                        }
                                        Log.v("dople", "task. fail()");
                                    } else {

                                        Log.v("dople", "task. success()");
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user.isEmailVerified()) {
                                            SimpleStore.saveString(DopleMainActivity.this, Config.USER_NAME, user.getDisplayName());
                                            SimpleStore.saveString(DopleMainActivity.this, Config.USER_EMAIL, user.getEmail());
                                            SimpleStore.saveString(DopleMainActivity.this, Config.USER_PASSWORD, mPassword.getText().toString());
                                            SimpleStore.saveString(DopleMainActivity.this, Config.USER_UID, user.getUid());
                                            Intent intent = new Intent(DopleMainActivity.this, DopleHomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            downloadProfileImage();
                                        } else {
                                            Toast.makeText(DopleMainActivity.this,"email ?????? ??????" ,Toast.LENGTH_SHORT).show();

                                        }
                                    }
                            }
                        });
    }

    private void downloadProfileImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storeRef = storage.getReference();
        storeRef.child(Config.FIREBASE_PROFILE_IMAGE_FIR + SimpleStore.getString(this, Config.USER_EMAIL) + ".jpg")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.v("dople", "downloadProfileImage onSuccess : " +  uri.toString());
                        SimpleStore.saveString(DopleMainActivity.this, Config.PROFILE_URI, uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("dople", "downloadProfileImage onFailure : " +  e.toString());
                        SimpleStore.saveString(DopleMainActivity.this, Config.PROFILE_URI, "");
                    }
                });
    }
}