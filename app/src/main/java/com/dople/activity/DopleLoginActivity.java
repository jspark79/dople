package com.dople.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dople.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class DopleLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private LinearLayout mAgreePage, mInfoPage;
    private CheckBox mAgreeAllBox, mAgreeTermsBox, mAgreePrivacyBox, mAgreePrivacyShareBox;
    private EditText mUserEmail, mUserPassword, mUserRePassword, mUserKickName;
    private Button mNextStepBtn, mRegisterDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_login);

        initView();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initView() {
        ImageButton mBackBtn = (ImageButton) findViewById(R.id.dople_login_back_btn);
        mBackBtn.setColorFilter(Color.parseColor("#ffffff"));
        mBackBtn.setOnClickListener(this);

        ImageButton mCloseBtn = (ImageButton) findViewById(R.id.dople_login_close_btn);
        mCloseBtn.setColorFilter(Color.parseColor("#ffffff"));
        mCloseBtn.setOnClickListener(this);

        mUserEmail = (EditText) findViewById(R.id.dople_login_user_email);
        mUserPassword = (EditText) findViewById(R.id.dople_login_user_password);
        mUserRePassword = (EditText) findViewById(R.id.dople_login_user_repassword);
        mUserKickName = (EditText) findViewById(R.id.dople_login_user_kickname);


        mNextStepBtn = (Button)findViewById(R.id.dople_login_next_step_btn);
        mNextStepBtn.setOnClickListener(this);
        ((Button)findViewById(R.id.dople_login_previous_stage_btn)).setOnClickListener(this);
        mRegisterDone = (Button)findViewById(R.id.dople_login_register_done);
        mRegisterDone.setOnClickListener(this);

        mAgreePage = (LinearLayout) findViewById(R.id.dople_login_agree_page);
        mInfoPage = (LinearLayout) findViewById(R.id.dople_login_info_page);

        mAgreeAllBox = (CheckBox) findViewById(R.id.dople_login_agree_all);
        mAgreeAllBox.setOnClickListener(this);
        mAgreeTermsBox = (CheckBox) findViewById(R.id.dople_login_agree_terms);
        mAgreeTermsBox.setOnClickListener(this);
        mAgreePrivacyBox = (CheckBox) findViewById(R.id.dople_login_agree_privacy);
        mAgreePrivacyBox.setOnClickListener(this);
        mAgreePrivacyShareBox = (CheckBox) findViewById(R.id.dople_login_agree_privacy_share);
        mAgreePrivacyShareBox.setOnClickListener(this);

        mUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0 ||
                        mUserPassword.getText().toString().isEmpty() ||
                        mUserRePassword.getText().toString().isEmpty() ||
                        mUserKickName.getText().toString().isEmpty()) {
                    mRegisterDone.setEnabled(false);
                } else {
                    mRegisterDone.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0 ||
                        mUserEmail.getText().toString().isEmpty() ||
                        mUserRePassword.getText().toString().isEmpty() ||
                        mUserKickName.getText().toString().isEmpty()) {
                    mRegisterDone.setEnabled(false);
                } else {
                    mRegisterDone.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mUserRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0 ||
                        mUserEmail.getText().toString().isEmpty() ||
                        mUserPassword.getText().toString().isEmpty() ||
                        mUserKickName.getText().toString().isEmpty()) {
                    mRegisterDone.setEnabled(false);
                } else {
                    mRegisterDone.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mUserKickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0 ||
                        mUserEmail.getText().toString().isEmpty() ||
                        mUserPassword.getText().toString().isEmpty() ||
                        mUserRePassword.getText().toString().isEmpty()) {
                    mRegisterDone.setEnabled(false);
                } else {
                    mRegisterDone.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dople_login_register_done:
                createUser();
                break;
            case R.id.dople_login_previous_stage_btn:
                mAgreeAllBox.setChecked(false);
                mAgreeTermsBox.setChecked(false);
                mAgreePrivacyBox.setChecked(false);
                mAgreePrivacyShareBox.setChecked(false);
                mNextStepBtn.setEnabled(false);
                mAgreePage.setVisibility(View.VISIBLE);
                mInfoPage.setVisibility(View.GONE);
                break;
            case R.id.dople_login_back_btn:
                onBackPressed();
                break;
            case R.id.dople_login_close_btn:
                break;
            case R.id.dople_login_next_step_btn:
                if (mAgreeTermsBox.isChecked() && mAgreePrivacyBox.isChecked() && mAgreePrivacyShareBox.isChecked()) {
                    mAgreePage.setVisibility(View.GONE);
                    mInfoPage.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dople_login_agree_all:
                if (mAgreeAllBox.isChecked()) {
                    mAgreeTermsBox.setChecked(true);
                    mAgreePrivacyBox.setChecked(true);
                    mAgreePrivacyShareBox.setChecked(true);
                    mNextStepBtn.setEnabled(true);
                } else {
                    mAgreeTermsBox.setChecked(false);
                    mAgreePrivacyBox.setChecked(false);
                    mAgreePrivacyShareBox.setChecked(false);
                    mNextStepBtn.setEnabled(false);
                }
                break;

            case R.id.dople_login_agree_terms:
            case R.id.dople_login_agree_privacy:
            case R.id.dople_login_agree_privacy_share:
                setAllAgreeCheckBox();
                break;
        }
    }

//    private EditText mUserEmail, mUserPassword, mUserRePassword, mUserKickName;
    private void createUser() {
        if (mUserEmail.getText().toString().isEmpty() ||
                mUserPassword.getText().toString().isEmpty() ||
                mUserRePassword.getText().toString().isEmpty() ||
                mUserKickName.getText().toString().isEmpty()) {
            Toast.makeText(DopleLoginActivity.this, "some filed is empty", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (!mUserPassword.getText().toString().equals(mUserRePassword.getText().toString())) {
            Toast.makeText(DopleLoginActivity.this, "password is different", Toast.LENGTH_SHORT).show();
            return ;
        }


        if (mUserPassword.getText().toString().length() < 6) {
            Toast.makeText(DopleLoginActivity.this, "password at least 6", Toast.LENGTH_SHORT).show();
            return ;
        }

        firebaseAuth.createUserWithEmailAndPassword(mUserEmail.getText().toString(), mUserPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                             ???????????? ?????????
//                            Toast.makeText(DopleLoginActivity.this, "???????????? ??????", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mUserKickName.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("dople", "User profile updated.");
                                            }
                                        }
                                    });

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                AlertDialog.Builder dialog = new AlertDialog.Builder(DopleLoginActivity.this);
                                                dialog.setTitle("?????? ??????")
                                                        .setMessage("?????? ????????? ????????? ???????????????.\n"+
                                                                "????????????\n" +
                                                                mUserEmail.getText().toString() + " ??????????????? ?????? ???????????? ?????????????????????.\n" +
                                                                "????????? ????????? ??????????????? ???????????? ??????????????? ????????????. \n\n" +
                                                                "???????????????")
                                                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                onBackPressed();
                                                            }
                                                        });
                                                dialog.show();

                                                Log.d("dople", "Email sent.");
                                            }
                                        }
                                    });
                        } else {
                            // ????????? ????????? ??????
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(DopleLoginActivity.this,"?????? ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"??????????????? ????????????.." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(DopleLoginActivity.this,"?????? ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"email ????????? ?????? ????????????." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(DopleLoginActivity.this,"?????? ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"?????????????????? email ?????????." ,Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Toast.makeText(DopleLoginActivity.this,"?????? ??????." ,Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(DopleMainActivity.this,"?????? ??????????????????.." ,Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(DopleLoginActivity.this, "?????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setAllAgreeCheckBox() {
        if (mAgreeTermsBox.isChecked() && mAgreePrivacyBox.isChecked() && mAgreePrivacyShareBox.isChecked()) {
            mAgreeAllBox.setChecked(true);
            mNextStepBtn.setEnabled(true);
        } else {
            mAgreeAllBox.setChecked(false);
            mNextStepBtn.setEnabled(false);
        }
    }
}