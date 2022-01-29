package com.example.mycalculator.ui;

import com.example.mycalculator.Calculator;
import com.example.mycalculator.Operation;

import java.math.BigDecimal;

public class CalculatorPresenter {

    private final CalculatorView view;
    private final Calculator calculator;

    private Double argOne = 0.0;
    private Double argTwo = null;
    private Operation previousOperation = null;
    private boolean onDot = false;
    private boolean onCalc = false;
    private int n = 1;


    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
    }

    public void onDotPressed() {
        onDot = true;
    }

    public void onDigitPressed(int digit) {


        if (previousOperation != null && !onDot) {
            argTwo = argTwo * 10 + digit;
            view.showResult(String.valueOf(argTwo));
        } else if (previousOperation != null) {
            argTwo = argTwo + digit / Math.pow(10, n);
            view.showResult(String.valueOf(argTwo));
            n++;
        } else if (onCalc) {
            argOne = 0.0;
            view.showOperand("");
            argOne = argOne * 10 + digit;
            view.showResult(String.valueOf(argOne));
            onCalc = false;
        } else if (!onDot) {
            view.showOperand("");
            argOne = argOne * 10 + digit;
            view.showResult(String.valueOf(argOne));
        } else {
            view.showOperand("");
            argOne = argOne + digit / Math.pow(10, n);
            view.showResult(String.valueOf(argOne));
            n++;
        }
    }

    public void onOperandPressed(Operation operation) {

        if (operation == Operation.SUM) {
            view.showOperand("+");
        }
        if (operation == Operation.SUB) {
            view.showOperand("-");
        }
        if (operation == Operation.DIV) {
            view.showOperand("/");
        }
        if (operation == Operation.MUL) {
            view.showOperand("*");
        }
        if (argTwo != null) {
            double result = calculator.performOperation(argOne, argTwo, previousOperation);
            view.showResult(String.valueOf(result));
            argOne = result;
        }
        argTwo = 0.0;
        n = 1;
        onDot = false;
        previousOperation = operation;
    }

    public void onCalcPressed() {
        if (argTwo != null) {
            double result = calculator.performOperation(argOne, argTwo, previousOperation);
            view.showResult(String.valueOf(result));
            view.showOperand("=");
            argOne = result;
            argTwo = null;
            onDot = false;
            onCalc = true;
            n = 1;
            previousOperation = null;
        }
    }

    public void onCleanPressed() {
        argOne = 0.0;
        argTwo = null;
        n = 1;
        onDot = false;
        previousOperation = null;
        view.showResult(String.valueOf(argOne));
        view.showOperand(null);
    }

    public void onResult(String result) {
        argOne = Double.parseDouble(result);
        if (argOne % 1 == 0) {
            n = 1;
        } else n = BigDecimal.valueOf(argOne).scale() + 1;
        onDot = argOne % 1 != 0;
        view.showResult(String.valueOf(argOne));
    }
}