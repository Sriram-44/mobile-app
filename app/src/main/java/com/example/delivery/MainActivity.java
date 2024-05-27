package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button settingButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        ImageButton settingButton = findViewById(R.id.settingButton);
        backButton = findViewById(R.id.backButton);
        TextView textViewWelcomeMessage = findViewById(R.id.textViewWelcomeMessage);
        Button buttonProfile = findViewById(R.id.buttonProfile);
        Button buttonOrders = findViewById(R.id.buttonOrders);
        Button buttonContact = findViewById(R.id.buttonContact);
        Button buttonCart = findViewById(R.id.buttonCart);

        // Set click listener for setting button
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runFragmentTransaction(new SettingsFragment());
            }
        });

        // Set click listener for back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // Set welcome message text
        textViewWelcomeMessage.setText("Main Page");

        // Set click listeners for profile, contact, and payment buttons
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runFragmentTransaction(new AccountFragment());
            }
        });

        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runFragmentTransaction(new CartFragment());
            }
        });

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runFragmentTransaction(new ContactFragment());
            }
        });

        // Load default fragment (e.g., OrdersFragment)
        runFragmentTransaction(new OrderFragment());
    }

    // Method to run fragment transaction
    private void runFragmentTransaction(final Fragment fragment) {
        Runnable fragmentTransactionRunnable = new Runnable() {
            @Override
            public void run() {
                loadFragment(fragment);
            }
        };
        fragmentTransactionRunnable.run();
    }

    // Method to load fragment into container
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
