package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    EditText distanceEditText, weightEditText;
    TextView costTextView;
    Button calculateButton, buttonConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        distanceEditText = findViewById(R.id.distanceEditText);
        weightEditText = findViewById(R.id.weightEditText);
        costTextView = findViewById(R.id.costTextView);
        calculateButton = findViewById(R.id.calculateButton);
        buttonConfirmOrder = findViewById(R.id.buttonConfirmOrder);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCost();
            }
        });

        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this, "Order confirmed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateCost() {
        String distanceStr = distanceEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();
        if (!distanceStr.isEmpty() && !weightStr.isEmpty()) {
            double distance = Double.parseDouble(distanceStr);
            double weight = Double.parseDouble(weightStr);
            double cost = calculateDeliveryCost(distance, weight);
            costTextView.setText("The cost of delivery is: $" + cost);
        } else {
            costTextView.setText("Please enter both distance and weight.");
        }
    }

    private double calculateDeliveryCost(double distance, double weight) {
        double baseCost = 10;
        double additionalCostPerKm = 1;

        if (distance <= 10) {
            return applyWeightModifier(baseCost, weight);
        } else if (distance <= 20) {
            double costForFirst10Km = applyWeightModifier(baseCost, weight);
            double additionalDistanceCost = (distance - 10) * additionalCostPerKm;
            return costForFirst10Km + applyWeightModifier(additionalDistanceCost, weight);
        } else {
            double costForFirst20Km = applyWeightModifier(baseCost + (10 * additionalCostPerKm), weight);
            double additionalDistance = distance - 20;
            double additionalDistanceCost = additionalDistance * (additionalCostPerKm + 1);
            return costForFirst20Km + applyWeightModifier(additionalDistanceCost, weight);
        }
    }

    private double applyWeightModifier(double cost, double weight) {
        if (weight < 2) {
            return cost;
        } else if (weight <= 10) {
            return cost * 1.5;
        } else {
            return cost * 2;
        }
    }
}
