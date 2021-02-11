package com.cst2355.khun0008;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);

        String savedString = prefs.getString("Email", "");
        EditText email = findViewById(R.id.edittext1);
        email.setText(savedString);

        Button loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(bt -> saveSharedPrefs(email.getText().toString()));

    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Email", stringToSave);
        editor.commit();
        }

}