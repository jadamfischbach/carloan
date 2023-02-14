package com.example.carloadcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ColorActivity extends AppCompatActivity {

    RadioButton greenButton;
    RadioButton cyanButton;
    RadioButton lavenderButton;
    RadioGroup radioGroup;

    private int backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        greenButton = findViewById(R.id.greenButton);
        cyanButton = findViewById(R.id.cyanButton);
        lavenderButton = findViewById(R.id.lavenderButton);
        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.greenButton)
                    backgroundColor = MainActivity.GREEN;
                else if (i == R.id.cyanButton)
                    backgroundColor = MainActivity.CYAN;
                else
                    backgroundColor = MainActivity.LAVENDER;
            }
        });

        Intent i = getIntent();
        backgroundColor = i.getIntExtra("color", 0xffff0000);

        if (backgroundColor == MainActivity.GREEN) {
            greenButton.setChecked(true);
        } else if (backgroundColor == MainActivity.CYAN) {
            cyanButton.setChecked(true);
        } else if (backgroundColor == MainActivity.LAVENDER) {
            lavenderButton.setChecked(true);
        }

    }

    @Override
    public void onPause()  {
        super.onPause();
        updateSharedPreferences();
    }

    private void updateSharedPreferences() {
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("background color", backgroundColor);
        editor.commit();
    }
}









