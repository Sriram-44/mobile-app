package com.example.delivery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;

public class CartFragment extends Fragment {

    private ListView cartListView;
    private ArrayAdapter<String> cartAdapter;
    private TextView productDetailsTextView;
    private Button payButton;
    private CartViewModel cartViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartListView = view.findViewById(R.id.cartListView);
        productDetailsTextView = view.findViewById(R.id.productDetailsTextView);
        payButton = view.findViewById(R.id.payButton);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        cartListView.setAdapter(cartAdapter);

        // Observe cart items
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), this::updateCartItems);

        cartListView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedItem = cartAdapter.getItem(position);
            showProductDetails(selectedItem);
        });

        payButton.setOnClickListener(v -> pay());

        return view;
    }

    private void showProductDetails(String selectedItem) {
        if (selectedItem == null) return;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                // Parse and show product details asynchronously
                return parseProductDetails(selectedItem);
            }

            @Override
            protected void onPostExecute(String productDetails) {
                super.onPostExecute(productDetails);
                productDetailsTextView.setText(productDetails);
            }
        }.execute();
    }

    private String parseProductDetails(String selectedItem) {
        // Parsing logic for product details
        String[] parts = selectedItem.split(" - ");
        if (parts.length >= 2) {
            String productName = parts[0];
            String productPrice = parts[1];
            String[] priceParts = productPrice.split(" ");
            if (priceParts.length >= 2) {
                double price = Double.parseDouble(priceParts[1].substring(1));
                int quantity = Integer.parseInt(priceParts[2].substring(1));
                double totalPrice = price * quantity;
                return "Product: " + productName + "\nPrice: " + priceParts[1] + "\nQuantity: " + quantity + "\nTotal Price: $" + totalPrice;
            }
        }
        return "";
    }

    private void updateCartItems(List<String> items) {
        if (items != null) {
            cartAdapter.clear();
            cartAdapter.addAll(items);
            cartAdapter.notifyDataSetChanged();
        }
    }

    private void pay() {
        Intent intent = new Intent(requireContext(), PaymentActivity.class);
        startActivity(intent);
    }
}
