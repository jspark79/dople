package com.dople.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dople.R;
import com.dople.util.Config;
import com.dople.util.SimpleStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DopleProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_IMAGE = 1;

    private String mCurPasswordStr;

    private EditText mUserName;
    private EditText mCurPassword, mNewPassword, mNewPasswordConfirm;

    private ImageView mUserImage;
    private Uri mUserImageUri = null;

    private ProgressDialog pDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_profile);

        mUserImageUri = null;

        initView();

        mCurPasswordStr = SimpleStore.getString(this, Config.USER_PASSWORD);
        pDailog = new ProgressDialog(this);
    }

    private void initView() {
        ImageButton backBtn = (ImageButton) findViewById(R.id.dople_profile_back_btn);
        backBtn.setColorFilter(Color.parseColor("#ffffff"));
        backBtn.setOnClickListener(this);

        TextView uerEmail = (TextView) findViewById(R.id.dople_profile_user_email);
        uerEmail.setText(SimpleStore.getString(this, Config.USER_EMAIL));

        mUserName = (EditText) findViewById(R.id.dople_profile_user_name);
        mUserName.setText(SimpleStore.getString(this, Config.USER_NAME));

        mCurPassword = (EditText)findViewById(R.id.dople_profile_user_curr_password);
        mNewPassword = (EditText)findViewById(R.id.dople_profile_user_new_password);
        mNewPasswordConfirm = (EditText)findViewById(R.id.dople_profile_user_new_password_confirm);

        ((Button) findViewById(R.id.dople_profile_save)).setOnClickListener(this);

        mUserImage = (ImageView) findViewById(R.id.dople_profile_user_image);
        mUserImage.setOnClickListener(this);

        String uri = SimpleStore.getString(this, Config.PROFILE_URI);
        Log.v("dople", "onCreate uri : " + uri);
        if (!uri.isEmpty())
            Glide.with(this).applyDefaultRequestOptions(new RequestOptions()
                .placeholder(R.drawable._11_user).error(R.drawable._11_user)).load(uri).into(mUserImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dople_profile_user_image:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
                break;
            case R.id.dople_profile_back_btn:
                onBackPressed();
                break;
            case R.id.dople_profile_save:
                save();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("dople", "onActivityResult");


        if ((resultCode == RESULT_OK) && (requestCode == PICK_IMAGE)) {
            try {
                mUserImageUri = data.getData();
                Log.v("dople", "onActivityResult uri : " + mUserImageUri.toString());
                final InputStream imageStream = getContentResolver().openInputStream(mUserImageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mUserImage.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    private void uploadImage(Uri imageUri) {
        Log.v("dople", "uploadImage : " +  imageUri.toString());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference();

        String filename = SimpleStore.getString(this, Config.USER_EMAIL) + ".jpg";

        StorageReference riversRef = ref.child(Config.FIREBASE_PROFILE_IMAGE_FIR + filename);
        UploadTask task = riversRef.putFile(imageUri);

        StorageReference delRef = ref.child(Config.FIREBASE_PROFILE_IMAGE_FIR + filename);
        delRef.delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("dople", "delRef onFailure : " +  e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.v("dople", "delRef onSuccess");
            }
        });


        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("dople", "task onFailure : " +  e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.v("dople", " task onSuccess");
                pDailog.dismiss();
                onBackPressed();
            }
        });

        pDailog.setMessage("저장중");
        pDailog.setCancelable(false);
        pDailog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        pDailog.show();
    }

    private void save() {
        if (mUserName.getText().toString().isEmpty()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("유저 이름 오류")
                    .setMessage("유저 이름을 확인해주세요.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            dialog.show();
            return;
        }
        if ((mCurPassword.getText().toString().length() > 0) &&
                ((!mCurPasswordStr.equals(mCurPassword.getText().toString())) ||
                        (!mNewPassword.getText().toString().equals(mNewPasswordConfirm.getText().toString())) ||
                        (!mCurPassword.getText().toString().equals(mNewPassword.getText().toString())) ||
                        (mNewPassword.getText().length() < 6))) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("비밀번호 오류")
                    .setMessage("비밀번호를 확인해주세요.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            dialog.show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (mNewPassword.getText().toString().length() > 0)
            user.updatePassword(mNewPassword.getText().toString());

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUserName.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("dople", "User profile updated.");

                            SimpleStore.saveString(DopleProfileActivity.this, Config.USER_NAME, mUserName.getText().toString());
                        }
                    }
                });

        if (mUserImageUri != null)
            uploadImage(mUserImageUri);
    }
}