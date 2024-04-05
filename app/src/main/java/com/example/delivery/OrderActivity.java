package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    EditText distanceEditText;
    TextView costTextView;
    Button calculateButton, buttonConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order); // Corrected layout

        distanceEditText = findViewById(R.id.distanceEditText);
        costTextView = findViewById(R.id.costTextView);
        calculateButton = findViewById(R.id.calculateButton);
        buttonConfirmOrder = findViewById(R.id.buttonConfirmOrder); // Initialize buttonConfirmOrder

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCost();
            }
        });

        // Set OnClickListener for buttonConfirmOrder
        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a toast message when the button is clicked
                Toast.makeText(OrderActivity.this, "Order confirmed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateCost() {
        String distanceStr = distanceEditText.getText().toString();
        if (!distanceStr.isEmpty()) {
            double distance = Double.parseDouble(distanceStr);
            double cost = calculateDeliveryCost(distance);
            costTextView.setText("The cost of delivery is: $" + cost);
        } else {
            costTextView.setText("Please enter a distance.");
        }
    }

    private double calculateDeliveryCost(double distance) {
        double baseCost = 10; // Cost for the first 10 km
        double additionalCostPerKm = 1; // Additional cost per km after the first 10 km

        if (distance <= 10) {
            return baseCost;
        } else if (distance <= 20) {
            return baseCost + (distance - 10) * additionalCostPerKm;
        } else {
            double costForFirst20Km = baseCost + (10 * additionalCostPerKm);
            double additionalDistance = distance - 20;
            return costForFirst20Km + (additionalDistance * (additionalCostPerKm + 1));
        }
    }
}
