package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button buttonProfile, buttonOrders, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonOrders = findViewById(R.id.buttonOrders);
        buttonLogout = findViewById(R.id.buttonLogout);

        overridePendingTransition(R.anim.slidein,R.anim.slideout);


        // Set click listeners for buttons
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProfileActivity
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        buttonOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to OrderActivity
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                finish();
            }
        });

        // Load MainFragment
        loadFragment(new MainFragment());
    }

    // Method to load fragment into container
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
