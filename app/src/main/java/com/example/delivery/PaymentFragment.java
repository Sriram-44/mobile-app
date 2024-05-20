package com.example.delivery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

        // Set total price (assuming it's passed as an argument)
        if (getArguments() != null) {
            double totalPrice = getArguments().getDouble("totalPrice", 0.0);
            textViewTotalPrice.setText("Total Price: $" + totalPrice);
        }

        // Handle payment button click
        buttonProceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToPayment();
            }
        });

        return view;
    }

    private void proceedToPayment() {
        // Get delivery distance
        double deliveryDistance = Double.parseDouble(editTextDeliveryDistance.getText().toString());

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
