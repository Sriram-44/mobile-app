package com.example.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private TextView userEmailTextView, userNameTextView, userCityTextView;

    public AccountFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get reference to TextViews in your layout file
        userEmailTextView = rootView.findViewById(R.id.emailTextView);
        userNameTextView = rootView.findViewById(R.id.usernameEditText);
        userCityTextView = rootView.findViewById(R.id.cityEditText);

        // Initialize Firebase Realtime Database reference
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, update UI with their email
            String userEmail = currentUser.getEmail();
            userEmailTextView.setText(userEmail);

            // Retrieve and display user's additional information (username and city)
            mDatabaseRef.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("username").getValue(String.class);
                        String userCity = dataSnapshot.child("city").getValue(String.class);

                        // Update UI with user's additional information
                        userNameTextView.setText(userName);
                        userCityTextView.setText(userCity);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                }
            });
        } else {
            // No user is signed in, redirect to login activity or handle as appropriate
        }
    }
}
