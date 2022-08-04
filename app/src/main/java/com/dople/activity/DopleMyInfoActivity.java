package com.dople.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

    private static final int PICK_IMAGE = 1;

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

        mProfileImage = (ImageView) findViewById(R.id.dople_myinfo_profile_image);
        mProfileImage.setOnClickListener(this);

        ImageButton backBtn = (ImageButton) findViewById(R.id.dople_myinfo_back_btn);

        ((TextView) findViewById(R.id.dople_myinfo_user_name)).setText(SimpleStore.getString(this, Config.KICK_NAME));
        ((TextView) findViewById(R.id.dople_myinfo_user_email)).setText(SimpleStore.getString(this, Config.USER_EMAIL));

        String uri = SimpleStore.getString(DopleMyInfoActivity.this, Config.PROFILE_URI);
        if (!uri.isEmpty())
            Glide.with(this).load(uri).into(mProfileImage);

        backBtn.setColorFilter(Color.parseColor("#ffffff"));
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.dople_myinfo_profile_image:
//                intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, PICK_IMAGE);
                break;
            case R.id.dolpe_myinfo_logout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(DopleMyInfoActivity.this, DopleMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.dolpe_myinfo_chat:
                intent = new Intent(DopleMyInfoActivity.this, DopleChatActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == RESULT_OK) && (requestCode == PICK_IMAGE)) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mProfileImage.setImageBitmap(selectedImage);
                uploadImage(imageUri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
//                Toast.makeText(PostImage.this, "Something went wrong", Toast.LENGTH_LONG).show();
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
                Log.v("dople", "onFailure : " +  e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
    }
}