package com.example.delivery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class AppSettingsActivity extends FontBaseActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREF_NOTIFICATIONS = "notifications";
    public static final String PREF_THEME = "theme";
    public static final String PREF_ACCESSIBILITY = "accessibility";
    public static final String PREF_FONT_SIZE = "font_size";

    private Switch notificationsSwitch;
    private Switch themeSwitch;
    private Switch accessibilitySwitch;
    private Button saveButton;

    private boolean notificationsEnabled;
    private boolean themeEnabled;
    private boolean accessibilityEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_app_setting);

        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        themeSwitch = findViewById(R.id.themeSwitch);
        accessibilitySwitch = findViewById(R.id.accessibilitySwitch);
        saveButton = findViewById(R.id.saveButton);

        SharedPreferences preferences = getSharedPreferences();
        notificationsEnabled = preferences.getBoolean(PREF_NOTIFICATIONS, false);
        themeEnabled = preferences.getBoolean(PREF_THEME, false);
        accessibilityEnabled = preferences.getBoolean(PREF_ACCESSIBILITY, false);

        notificationsSwitch.setChecked(notificationsEnabled);
        themeSwitch.setChecked(themeEnabled);
        accessibilitySwitch.setChecked(accessibilityEnabled);

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> notificationsEnabled = isChecked);

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> themeEnabled = isChecked);

        accessibilitySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> accessibilityEnabled = isChecked);

        saveButton.setOnClickListener(v -> savePreferences());
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_NOTIFICATIONS, notificationsEnabled);
        editor.putBoolean(PREF_THEME, themeEnabled);
        editor.putBoolean(PREF_ACCESSIBILITY, accessibilityEnabled);
        editor.apply();

        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
    }

    protected SharedPreferences getSharedPreferences() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
}
