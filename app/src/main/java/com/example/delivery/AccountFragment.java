package com.example.delivery;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
            new LoadUserInfoTask().execute(currentUser.getUid());
        } else {
            // No user is signed in, redirect to login activity or handle as appropriate
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadUserInfoTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... uids) {
            final User[] user = {null};
            mDatabaseRef.child(uids[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("username").getValue(String.class);
                        String userCity = dataSnapshot.child("city").getValue(String.class);
                        user[0] = new User(userName, userCity);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
            return user[0];
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                userNameTextView.setText(user.getUserName());
                userCityTextView.setText(user.getUserCity());
            }
        }
    }

    private static class User {
        private final String userName;
        private final String userCity;

        public User(String userName, String userCity) {
            this.userName = userName;
            this.userCity = userCity;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserCity() {
            return userCity;
        }
    }
}
