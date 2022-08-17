package com.dople.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dople.R;
import com.dople.util.Config;
import com.dople.util.SimpleStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DopleMyInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mProfileImage;
    private TextView mUserName;

    @Override
    protected void onResume() {
        super.onResume();
        downloadProfileImage();
        mUserName.setText(SimpleStore.getString(this, Config.USER_NAME));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_my_info);

        initView();
    }

    private void initView() {
        ((RelativeLayout) findViewById(R.id.dolpe_myinfo_register)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.dolpe_myinfo_chat)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.dolpe_myinfo_logout)).setOnClickListener(this);
        ((RelativeLayout) findViewById(R.id.dolpe_myinfo_update_profile)).setOnClickListener(this);

        mProfileImage = (ImageView) findViewById(R.id.dople_myinfo_profile_image);
        mProfileImage.setOnClickListener(this);

        ImageButton backBtn = (ImageButton) findViewById(R.id.dople_myinfo_back_btn);

        mUserName = (TextView) findViewById(R.id.dople_myinfo_user_name);
        mUserName.setText(SimpleStore.getString(this, Config.USER_NAME));
        ((TextView) findViewById(R.id.dople_myinfo_user_email)).setText(SimpleStore.getString(this, Config.USER_EMAIL));

        backBtn.setColorFilter(Color.parseColor("#ffffff"));
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.dolpe_myinfo_update_profile:
                intent = new Intent(DopleMyInfoActivity.this, DopleProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.dolpe_myinfo_logout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(DopleMyInfoActivity.this, DopleMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.dolpe_myinfo_chat:
                intent = new Intent(DopleMyInfoActivity.this, DopleChatListActivity.class);
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

    private void downloadProfileImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storeRef = storage.getReference();
        storeRef.child(Config.FIREBASE_PROFILE_IMAGE_FIR + SimpleStore.getString(this, Config.USER_EMAIL) + ".jpg")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.v("dople", "downloadProfileImage onSuccess : " +  uri.toString());
                        SimpleStore.saveString(DopleMyInfoActivity.this, Config.PROFILE_URI, uri.toString());
//                        String uri = SimpleStore.getString(DopleMyInfoActivity.this, Config.PROFILE_URI);

                        if (!uri.toString().isEmpty())
//            Glide.with(this).load(uri).into(mProfileImage);
                            Glide.with(DopleMyInfoActivity.this).applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.drawable._11_user).error(R.drawable._11_user)).load(uri.toString()).into(mProfileImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("dople", "downloadProfileImage onFailure : " +  e.toString());
                        SimpleStore.saveString(DopleMyInfoActivity.this, Config.PROFILE_URI, "");
                    }
                });
    }
}