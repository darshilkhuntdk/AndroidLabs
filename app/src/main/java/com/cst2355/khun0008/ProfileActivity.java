package com.cst2355.khun0008;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    Button goToChat;
    ImageButton mImageButton;
    EditText emailField;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME,"In function: "+"onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImageButton = findViewById(R.id.imageButton);
        mImageButton.setOnClickListener((clk) -> {
            dispatchTakePictureIntent();
        });

        Intent fromMain = getIntent();
        String emailText = fromMain.getStringExtra("email");

        emailField = findViewById(R.id.editTextEmail);
        emailField.setText(emailText);

        Intent chat = new Intent(this,ChatRoomActivity.class);
        goToChat = findViewById(R.id.goToChat);
        goToChat.setOnClickListener((click) -> {
            startActivityForResult(chat,12);
        });
        }

    private void dispatchTakePictureIntent () {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(ACTIVITY_NAME,"In function: "+ "onActivityResult()");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }
}

