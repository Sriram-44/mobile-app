package com.example.delivery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends FontBaseActivity {

    private Button buttonProfile, buttonOrders, buttonContact, buttonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonOrders = findViewById(R.id.buttonOrders);
        buttonContact = findViewById(R.id.buttonContact); // Corrected
        buttonSettings = findViewById(R.id.buttonSettings);

        // Set click listeners for buttons
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AccountFragment());
            }
        });

        buttonOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new OrderFragment());
            }
        });

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ContactFragment()); // Corrected
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SettingsFragment());
            }
        });

        // Load default fragment (e.g., OrdersFragment)
        loadFragment(new OrderFragment());
    }

    // Method to load fragment into container
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }
}
