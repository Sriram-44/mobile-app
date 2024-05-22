package com.example.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.delivery.EmailSender;
import com.google.android.material.button.MaterialButton;

import javax.mail.MessagingException;

public class PaymentActivity extends AppCompatActivity {

    private EditText etCardNumber, etExpiryDate, etCVV;
    private MaterialButton btnPay;
    private LottieAnimationView loadingAnimation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCVV = findViewById(R.id.etCVV);
        btnPay = findViewById(R.id.btnPay);
        loadingAnimation = findViewById(R.id.loadingAnimation);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment();
            }
        });
    }

    private void handlePayment() {
        String cardNumber = etCardNumber.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();
        String cvv = etCVV.getText().toString().trim();

        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(PaymentActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();
        btnPay.setEnabled(false);

        // Simulate payment processing
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingAnimation.setVisibility(View.GONE);
                loadingAnimation.cancelAnimation();
                btnPay.setEnabled(true);

                // Handle payment success or failure
                Toast.makeText(PaymentActivity.this, "Payment successful", Toast.LENGTH_SHORT).show();

                // Send email notification
                try {
                    EmailSender.sendEmail("recipient@example.com", "Order Registered", "Your order has been successfully registered.");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    Toast.makeText(PaymentActivity.this, "Failed to send email notification", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);
    }
}
