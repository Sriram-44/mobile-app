package com.example.delivery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class FontBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        // Apply the selected font size
        applyFontSize();
    }

    private void applyFontSize() {
        SharedPreferences preferences = getSharedPreferences(AppSettingsActivity.PREFS_NAME, MODE_PRIVATE);
        String fontSize = preferences.getString(AppSettingsActivity.PREF_FONT_SIZE, "Medium");


        float textSize;
        switch (fontSize) {
            case "Small":
                textSize = 12f;
                break;
            case "Large":
                textSize = 18f;
                break;
            case "Extra Large":
                textSize = 22f;
                break;
            case "Medium":
            default:
                textSize = 14f;
                break;
        }

        // Apply the text size to the root view or other specific views
        View rootView = findViewById(android.R.id.content);
        applyFontSizeToViewGroup((ViewGroup) rootView, textSize);
    }

    private void applyFontSizeToViewGroup(ViewGroup viewGroup, float textSize) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            } else if (view instanceof ViewGroup) {
                applyFontSizeToViewGroup((ViewGroup) view, textSize);
            }
        }
    }

    // Abstract method to get the layout resource ID for each activity
    protected abstract int getLayoutResourceId();
}
