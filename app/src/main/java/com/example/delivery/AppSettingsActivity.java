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

        // Load saved preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        notificationsEnabled = settings.getBoolean(PREF_NOTIFICATIONS, false);
        themeEnabled = settings.getBoolean(PREF_THEME, false);
        accessibilityEnabled = settings.getBoolean(PREF_ACCESSIBILITY, false);

        notificationsSwitch.setChecked(notificationsEnabled);
        themeSwitch.setChecked(themeEnabled);
        accessibilitySwitch.setChecked(accessibilityEnabled);

        // Set listeners for switch changes
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notificationsEnabled = isChecked;
            }
        });

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                themeEnabled = isChecked;
            }
        });

        accessibilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                accessibilityEnabled = isChecked;
            }
        });

        // Set listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_app_setting;
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREF_NOTIFICATIONS, notificationsEnabled);
        editor.putBoolean(PREF_THEME, themeEnabled);
        editor.putBoolean(PREF_ACCESSIBILITY, accessibilityEnabled);
        editor.apply();

        // Show a toast message indicating the settings were saved
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
    }
}
