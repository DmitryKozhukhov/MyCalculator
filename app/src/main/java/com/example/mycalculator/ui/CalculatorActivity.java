package com.example.mycalculator.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycalculator.CalculatorImp;
import com.example.mycalculator.Operation;
import com.example.mycalculator.R;
import com.example.mycalculator.Theme;
import com.example.mycalculator.ThemeStorage;

import java.util.HashMap;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView {

    private TextView txtResult;
    private TextView txtOperand;

    private CalculatorPresenter presenter;

    private static final String ARG_RESULT = "ARG_RESULT";
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThemeStorage storage = new ThemeStorage(this);

        ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Theme theme = (Theme) result.getData().getSerializableExtra(SelectThemeActivity.THEME_RESULT);

                    storage.saveTheme(theme);
                    recreate();
                }
            }
        });

        setTheme(storage.getTheme().getStyle());

        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txt_result);
        txtOperand = findViewById(R.id.txt_operand);

        presenter = new CalculatorPresenter(this, new CalculatorImp());

        if (savedInstanceState != null) {
            result = savedInstanceState.getString(ARG_RESULT);
            txtResult.setText(result);
            presenter.onResult(result);
        } else txtResult.setText("0");

        findViewById(R.id.key_dot).setOnClickListener(v -> presenter.onDotPressed());

        HashMap<Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.key_0, 0);
        digits.put(R.id.key_1, 1);
        digits.put(R.id.key_2, 2);
        digits.put(R.id.key_3, 3);
        digits.put(R.id.key_4, 4);
        digits.put(R.id.key_5, 5);
        digits.put(R.id.key_6, 6);
        digits.put(R.id.key_7, 7);
        digits.put(R.id.key_8, 8);
        digits.put(R.id.key_9, 9);

        View.OnClickListener digitClickListener = v -> presenter.onDigitPressed(digits.get(v.getId()));

        findViewById(R.id.key_0).setOnClickListener(digitClickListener);
        findViewById(R.id.key_1).setOnClickListener(digitClickListener);
        findViewById(R.id.key_2).setOnClickListener(digitClickListener);
        findViewById(R.id.key_3).setOnClickListener(digitClickListener);
        findViewById(R.id.key_4).setOnClickListener(digitClickListener);
        findViewById(R.id.key_5).setOnClickListener(digitClickListener);
        findViewById(R.id.key_6).setOnClickListener(digitClickListener);
        findViewById(R.id.key_7).setOnClickListener(digitClickListener);
        findViewById(R.id.key_8).setOnClickListener(digitClickListener);
        findViewById(R.id.key_9).setOnClickListener(digitClickListener);

        HashMap<Integer, Operation> operands = new HashMap<>();
        operands.put(R.id.key_div, Operation.DIV);
        operands.put(R.id.key_mul, Operation.MUL);
        operands.put(R.id.key_minus, Operation.SUB);
        operands.put(R.id.key_plus, Operation.SUM);

        View.OnClickListener operandClickListener = v -> presenter.onOperandPressed(operands.get(v.getId()));

        findViewById(R.id.key_div).setOnClickListener(operandClickListener);
        findViewById(R.id.key_mul).setOnClickListener(operandClickListener);
        findViewById(R.id.key_minus).setOnClickListener(operandClickListener);
        findViewById(R.id.key_plus).setOnClickListener(operandClickListener);

        findViewById(R.id.key_ac).setOnClickListener(v -> presenter.onCleanPressed());

        findViewById(R.id.key_result).setOnClickListener(v -> presenter.onCalcPressed());

        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsLauncher.launch(SelectThemeActivity.intent(CalculatorActivity.this, storage.getTheme()));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        result = String.valueOf(txtResult.getText());
        outState.putString(ARG_RESULT, result);
    }

    @Override
    public void showResult(String value) {
        txtResult.setText(value);
    }

    @Override
    public void showOperand(String operand) {
        txtOperand.setText(operand);
    }


}