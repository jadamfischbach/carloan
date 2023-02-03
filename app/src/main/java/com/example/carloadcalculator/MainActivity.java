package com.example.carloadcalculator;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

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

    private EditText carCostEdit;
    private EditText downPaymentEdit;
    private EditText interestEdit;
    private RadioGroup radioGroup;
    private RadioButton loanRadioButton;
    private TextView seekBarLabel;
    private SeekBar seekBar;
    private EditText paymentDisplay;
    private Button button;

    private boolean loan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        EditListener el = new EditListener();
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

    public void calculate() {
        double cost = Double.parseDouble(carCostEdit.getText().toString());
        double down = Double.parseDouble(downPaymentEdit.getText().toString());
        double apr = Double.parseDouble(interestEdit.getText().toString()) / 100;
        double mpr = apr/12;
        int length = seekBar.getProgress();
        double principle = loan ? cost - down : (cost / 3) - down;
        double payment = principle * (mpr / (1-Math.pow(1+mpr, -length)));
        paymentDisplay.setText("$" + String.format("%.2f", payment));
    }

    private class EditListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            calculate();
            return false;
        }
    }

}