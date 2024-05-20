package com.example.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PaymentFragment extends Fragment {

    private TextView textViewTotalPrice;
    private EditText editTextDeliveryDistance;
    private RadioGroup radioGroupPaymentMethod;
    private Button buttonProceedToPayment;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // Find views
        textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice);
        editTextDeliveryDistance = view.findViewById(R.id.editTextDeliveryDistance);
        radioGroupPaymentMethod = view.findViewById(R.id.radioGroupPaymentMethod);
        buttonProceedToPayment = view.findViewById(R.id.buttonProceedToPayment);

        // Handle payment button click
        buttonProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToPayment();
            }
        });

        // Calculate and display total price
        calculateTotalPrice();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void calculateTotalPrice() {
        // Retrieve passed arguments
        Bundle args = getArguments();
        if (args != null) {
            String productPriceStr = args.getString("productPrice", "0.00").replace("$", "");
            double productPrice = Double.parseDouble(productPriceStr);
            int quantity = args.getInt("quantity", 1);

            // Get delivery distance
            double deliveryDistance = 0.0;
            if (!editTextDeliveryDistance.getText().toString().isEmpty()) {
                deliveryDistance = Double.parseDouble(editTextDeliveryDistance.getText().toString());
            }

            // Calculate total price
            double totalPrice = productPrice * quantity;

            // Calculate additional cost based on distance
            if (deliveryDistance <= 10) {
                totalPrice += deliveryDistance; // $1 for each km up to 10 km
            } else {
                totalPrice += 10; // $1 for each km up to 10 km
                double additionalDistance = deliveryDistance - 10;
                totalPrice += Math.ceil(additionalDistance / 5) * 1; // $1 for every additional 5 km
            }

            textViewTotalPrice.setText("Total Price: $" + String.format("%.2f", totalPrice));
        }
    }


    private void proceedToPayment() {
        // Get selected payment method
        int selectedPaymentMethodId = radioGroupPaymentMethod.getCheckedRadioButtonId();
        RadioButton radioButtonSelected = getView().findViewById(selectedPaymentMethodId);
        String selectedPaymentMethod = radioButtonSelected.getText().toString();

        // Proceed with payment based on the selected method
        if (selectedPaymentMethod.equals("UPI") || selectedPaymentMethod.equals("Card")) {
            // Implement payment process
            // Example: Start payment activity
        } else if (selectedPaymentMethod.equals("Cash on Delivery")) {
            // Proceed with order confirmation
            confirmOrder();
        }
    }

    private void confirmOrder() {
        // Implement order confirmation process
        // Example: Show confirmation dialog
    }
}
