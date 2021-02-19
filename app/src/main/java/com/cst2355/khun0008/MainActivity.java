package com.cst2355.khun0008;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);

        String savedString = prefs.getString("Email", "");
        email = findViewById(R.id.edittext1);
        email.setText(savedString);

        Button loginButton = findViewById(R.id.loginbutton);
        Intent profile = new Intent(this,ProfileActivity.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPrefs(email.getText().toString());
                profile.putExtra("email",email.getText().toString());
                startActivityForResult(profile,35);
            }
        });

    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Email", stringToSave);
        editor.commit();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}