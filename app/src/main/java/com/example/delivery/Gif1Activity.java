package com.example.delivery;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Gif1Activity extends AppCompatActivity {

    private LinearLayout detailsLayout;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    private Spinner quantitySpinner;
    private Button addToCartButton;
    private Button backButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gif1);

        setupWindowInsets();
        initializeFirebase();
        setupActivity();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("cartItems");
    }

    private void setupActivity() {
        detailsLayout = findViewById(R.id.detailsLayout);
        productNameTextView = findViewById(R.id.productName);
        productPriceTextView = findViewById(R.id.productPrice);
        quantitySpinner = findViewById(R.id.quantitySpinner);
        addToCartButton = findViewById(R.id.addToCartButton);
        backButton = findViewById(R.id.backButton);

        // Set up the Spinner with quantity options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(adapter);

        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);

        imageView1.setOnClickListener(v -> showProductDetails("Apple", "$10.00"));
        imageView2.setOnClickListener(v -> showProductDetails("Tea", "$15.00"));

        addToCartButton.setOnClickListener(view -> {
            int selectedQuantity = Integer.parseInt(quantitySpinner.getSelectedItem().toString());
            addToCart(productNameTextView.getText().toString(), productPriceTextView.getText().toString(), selectedQuantity);
        });

        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void showProductDetails(String productName, String price) {
        productNameTextView.setText(productName);
        productPriceTextView.setText("Price: " + price);
        detailsLayout.setVisibility(View.VISIBLE);
    }

    private void addToCart(String productName, String price, int quantity) {
        String id = databaseReference.push().getKey();
        CartItem cartItem = new CartItem(id, productName, price, quantity);
        databaseReference.child(id).setValue(cartItem)
                .addOnSuccessListener(aVoid -> Toast.makeText(Gif1Activity.this, "Added to cart", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(Gif1Activity.this, "Failed to add to cart", Toast.LENGTH_SHORT).show());
    }

    public static class CartItem {
        public String id;
        public String productName;
        public String price;
        public int quantity;

        public CartItem() {
            // Default constructor required for calls to DataSnapshot.getValue(CartItem.class)
        }

        public CartItem(String id, String productName, String price, int quantity) {
            this.id = id;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }
    }
}
