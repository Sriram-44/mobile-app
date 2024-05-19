package com.example.delivery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class AppSettingsActivity extends FontBaseActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREF_NOTIFICATIONS = "notifications";
    public static final String PREF_THEME = "theme";
    public static final String PREF_ACCESSIBILITY = "accessibility";
    public static final String PREF_FONT_SIZE = "Medium";

    private Switch notificationsSwitch;
    private Switch themeSwitch;
    private Switch accessibilitySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_app_setting);

        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        themeSwitch = findViewById(R.id.themeSwitch);
        accessibilitySwitch = findViewById(R.id.accessibilitySwitch);

        // Load saved preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        notificationsSwitch.setChecked(settings.getBoolean(PREF_NOTIFICATIONS, false));
        themeSwitch.setChecked(settings.getBoolean(PREF_THEME, false));
        accessibilitySwitch.setChecked(settings.getBoolean(PREF_ACCESSIBILITY, false));

        // Set listeners for switch changes
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePreference(PREF_NOTIFICATIONS, isChecked);
            }
        });

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePreference(PREF_THEME, isChecked);
            }
        });

        accessibilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePreference(PREF_ACCESSIBILITY, isChecked);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_app_setting;
    }


    private void savePreference(String key, boolean value) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();

        // Show a toast message indicating the setting change
        String message = key + " setting changed to " + value;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
