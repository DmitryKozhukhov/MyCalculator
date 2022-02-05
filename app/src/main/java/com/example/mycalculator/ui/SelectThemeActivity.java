package com.example.mycalculator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycalculator.R;
import com.example.mycalculator.Theme;
import com.example.mycalculator.ThemeStorage;

public class SelectThemeActivity extends AppCompatActivity {

    private static final String ARG_THEME = "ARG_THEME";

    public static final String THEME_RESULT = "THEME_RESULT";

    public static Intent intent(Context context, Theme theme) {

        Intent intent = new Intent(context, SelectThemeActivity.class);

        intent.putExtra(ARG_THEME, theme);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_theme);

        LinearLayout container = findViewById(R.id.container);

        Theme selectedTheme = (Theme)getIntent().getSerializableExtra(ARG_THEME);

        for (Theme theme : Theme.values()) {

            View view = getLayoutInflater().inflate(R.layout.item_theme, container, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putExtra(THEME_RESULT, theme);
                    setResult(Activity.RESULT_OK,data);
                    finish();
                }
            });

            TextView themeTitle = view.findViewById(R.id.theme_title);
            themeTitle.setText(theme.getTitle());

            ImageView check = view.findViewById(R.id.img_check);

            if (theme.equals(selectedTheme)){
                check.setVisibility(View.VISIBLE);
            } else {
                check.setVisibility(View.GONE);
            }

            container.addView(view);
        }
    }
}