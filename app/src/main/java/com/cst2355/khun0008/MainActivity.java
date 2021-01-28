package com.cst2355.khun0008;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        Button myBtn = findViewById(R.id.button2);
        myBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, getString(R.string.toast_message), Toast.LENGTH_LONG).show();
        });

    }
}