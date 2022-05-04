package com.projects.Screen1;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PatternPopup extends Activity{

    private TextView explanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pattern_popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;

        getWindow().setLayout((int)(width*.85), LinearLayout.LayoutParams.WRAP_CONTENT);

        explanation = findViewById(R.id.fieldPattern_tv);
        explanation.setText(getIntent().getStringExtra("explanation"));
    }
}
