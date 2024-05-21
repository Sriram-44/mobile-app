package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Gif1Activity extends AppCompatActivity {

    private LinearLayout detailsLayout;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    private Spinner quantitySpinner;
    private Button addToCartButton;
    private Button backButton;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif1);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        setupActivity();
    }

    private void setupActivity() {
        detailsLayout = findViewById(R.id.detailsLayout);
        productNameTextView = findViewById(R.id.productName);
        productPriceTextView = findViewById(R.id.productPrice);
        quantitySpinner = findViewById(R.id.quantitySpinner);
        addToCartButton = findViewById(R.id.addToCartButton);
        backButton = findViewById(R.id.backButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(adapter);

        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);

        imageView1.setOnClickListener(v -> showProductDetails("Apple", "$10.00"));
        imageView2.setOnClickListener(v -> showProductDetails("Tea", "$15.00"));

        // Assume you have a method to get the current username
        String username = getUsername();

        addToCartButton.setOnClickListener(view -> {
            int selectedQuantity = Integer.parseInt(quantitySpinner.getSelectedItem().toString());
            String productName = productNameTextView.getText().toString();
            String productPrice = productPriceTextView.getText().toString();

            // Generate a unique item ID (you can use UUID or Firebase's push key)
            String itemId = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Cart").push().getKey();

            // Create the cartItem with username
            cartItem cartItem = new cartItem(itemId, productName, productPrice, selectedQuantity, username);

            // Save to Firebase under the user's cart
            DatabaseReference cartItemRef = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Cart").child(itemId);
            cartItemRef.setValue(cartItem);
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gif1Activity.this, MainActivity.class));
            }
        });
    }

    private void showProductDetails(String productName, String price) {
        productNameTextView.setText(productName);
        productPriceTextView.setText("Price: " + price);
        detailsLayout.setVisibility(View.VISIBLE);
    }

    // Mock method to get the current username
    private String getUsername() {
        // Replace this with actual logic to get the logged-in user's username
        return "exampleUsername";
    }
}
