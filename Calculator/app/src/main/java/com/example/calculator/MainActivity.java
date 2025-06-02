package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private String currentInput = "";
    private double firstOperand = Double.NaN;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
    }

    public void onDigitClick(View view) {
        Button button = (Button) view;
        currentInput += button.getTag().toString();
        updateResultTextView();
    }

    public void onOperatorClick(View view) {
        if (!currentInput.isEmpty()) {
            operator = ((Button) view).getText().toString();
            if (Double.isNaN(firstOperand)) {
                firstOperand = Double.parseDouble(currentInput);
            }
            currentInput = "";
        }
    }

    public void onDecimalClick(View view) {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            updateResultTextView();
        }
    }

    // Clear the view
    public void onClearClick(View view) {
        currentInput = "";
        firstOperand = Double.NaN;
        operator = "";
        updateResultTextView();
    }

    public void onEqualClick(View view) {
        if (!Double.isNaN(firstOperand) && !currentInput.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = performOperation(firstOperand, secondOperand, operator);
            currentInput = String.valueOf(result);
            operator = "";
            firstOperand = result;
            updateResultTextView();
        }
    }

    private double performOperation(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    return Double.NaN; // Division by zero
                }
            default:
                return num2;
        }
    }

    private void updateResultTextView() {
        resultTextView.setText(currentInput);
    }
}
