package com.example.delivery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<List<String>> cartItems = new MutableLiveData<>();

    public CartViewModel() {
        // Initialize with some data or empty list
        cartItems.setValue(new ArrayList<>());
    }

    public LiveData<List<String>> getCartItems() {
        return cartItems;
    }

    public void addCartItem(String item) {
        List<String> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.add(item);
            cartItems.setValue(currentItems);
        }
    }

    public void removeCartItem(String item) {
        List<String> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.remove(item);
            cartItems.setValue(currentItems);
        }
    }
}
