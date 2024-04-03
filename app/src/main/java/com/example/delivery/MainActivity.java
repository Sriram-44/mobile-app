package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonProfile, buttonOrders, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonOrders = findViewById(R.id.buttonOrders);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Set click listeners for buttons
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle profile button click
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout button click
                // You can add your logout logic here
            }
        });
    }
}
