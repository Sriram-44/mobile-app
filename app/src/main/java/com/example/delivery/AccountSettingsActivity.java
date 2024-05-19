package com.example.delivery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

public class AccountSettingsActivity extends FontBaseActivity {

    private EditText usernameEditText, emailEditText, cityEditText, passwordEditText;
    private Button updateButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account_setting);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        cityEditText = findViewById(R.id.cityEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        updateButton = findViewById(R.id.updateButton);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            emailEditText.setText(currentUser.getEmail());
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_account_setting;
    }

    private void updateUserProfile() {
        String username = usernameEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(password)) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                currentUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AccountSettingsActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AccountSettingsActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            mDatabaseRef.child(userId).child("username").setValue(username);
            mDatabaseRef.child(userId).child("city").setValue(city);
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
