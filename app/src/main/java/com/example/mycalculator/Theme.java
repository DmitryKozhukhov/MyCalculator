package com.example.mycalculator;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

public enum Theme {
    ONE(R.style.Theme_MyCalculator, R.string.theme_one, "one"),
    TWO(R.style.Theme_MyCalculatorV2, R.string.theme_two, "two"),
    THREE(R.style.Theme_MyCalculatorV3, R.string.theme_three, "three");

    @StyleRes
    private final int style;

    @StringRes
    private final int title;

    private final String key;

    Theme(int style, int title, String key) {
        this.style = style;
        this.title = title;
        this.key = key;
    }

    public int getStyle() {
        return style;
    }

    public String getKey() {
        return key;
    }

    public int getTitle() {
        return title;
    }

}
