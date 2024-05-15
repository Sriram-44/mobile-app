package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private TextView userEmailTextView, userNameTextView, userCityTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get reference to TextViews in your layout file
        userEmailTextView = findViewById(R.id.user_email_text_view);
        userNameTextView = findViewById(R.id.user_name_text_view);
        userCityTextView = findViewById(R.id.user_city_text_view);

        // Initialize Firebase Realtime Database reference
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    protected void onStart() {
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
