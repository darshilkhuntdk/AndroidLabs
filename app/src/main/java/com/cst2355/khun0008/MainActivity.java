package com.cst2355.khun0008;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        Button myBtn = findViewById(R.id.button2);
        myBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, getString(R.string.toast_message), Toast.LENGTH_LONG).show();
        });

        Switch onOffSwitch = findViewById(R.id.switch1);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Snackbar.make(onOffSwitch,isChecked?"The switch is now on":"The switch is now off",Snackbar.LENGTH_LONG).show();
                //Snackbar.setAction("UNDO",click -> buttonView.setChecked(!isChecked));
            }
        });



    }
}