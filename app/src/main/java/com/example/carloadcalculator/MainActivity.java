package com.example.carloadcalculator;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int GREEN = 0xff50c878;
    public static final int CYAN = 0xff00ffff;
    public static final int LAVENDER = 0xffe6e6fa;

    private EditText carCostEdit;
    private EditText downPaymentEdit;
    private EditText interestEdit;
    private RadioGroup radioGroup;
    private RadioButton loanRadioButton;
    private TextView seekBarLabel;
    private SeekBar seekBar;
    private EditText paymentDisplay;
    private Button button;
    private Button colorButton;
    private ConstraintLayout layout;

    private boolean loan;
    private int backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundColor = CYAN;

        layout = findViewById(R.id.layout);
        colorButton = findViewById(R.id.colorButton);
        carCostEdit = findViewById(R.id.car_cost_edit);
        downPaymentEdit = findViewById(R.id.down_payment_edit);
        interestEdit = findViewById(R.id.interest_edit);
        paymentDisplay = findViewById(R.id.payment_display);
        loanRadioButton =  findViewById(R.id.loan_radio_button);
        radioGroup = findViewById(R.id.radio_group);
        seekBarLabel = findViewById(R.id.seek_bar_label);
        seekBar = findViewById(R.id.seek_bar);
        button = findViewById(R.id.calc_button);

        loan = true;

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ColorActivity.class);
                i.putExtra("color", backgroundColor);
                startActivity(i);
            }
        });

        EditListener el = new EditListener();
        carCostEdit.setOnEditorActionListener(el);
        interestEdit.setOnEditorActionListener(el);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        loan = i == R.id.loan_radio_button;
                        calculate();
                    }
                }
        );

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        seekBarLabel.setText(seekBar.getProgress()+"");
                        calculate();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }

    private void updateColor() {
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        backgroundColor = sp.getInt("background color", 0xff0000ff);
        layout.setBackgroundColor(backgroundColor);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateColor();
    }

    public void calculate() {
        try {
            double cost = Double.parseDouble(carCostEdit.getText().toString());
            double down = Double.parseDouble(downPaymentEdit.getText().toString());
            double apr = Double.parseDouble(interestEdit.getText().toString()) / 100;
            double mpr = apr / 12;
            int length = seekBar.getProgress();
            double principle = loan ? cost - down : (cost / 3) - down;
            double payment = principle * (mpr / (1 - Math.pow(1 + mpr, -length)));
            paymentDisplay.setText("$" + String.format("%.2f", payment));
        } catch (NumberFormatException ex) {
            paymentDisplay.setText("Must fill in all input boxes.");
        }
    }

    private class EditListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            calculate();
            return false;
        }
    }

}