package com.example.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextEmailOrPhone, editTextCity, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmailOrPhone = findViewById(R.id.editTextEmailOrPhone);
        editTextCity = findViewById(R.id.editTextCity);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        // Set OnClickListener for SignUp button
        buttonSignUp.setOnClickListener(view -> signUp());
    }

    private void signUp() {
        String userName = editTextUserName.getText().toString().trim();
        String emailOrPhone = editTextEmailOrPhone.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Perform validation
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(emailOrPhone) || TextUtils.isEmpty(city)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform sign up with email
        mAuth.createUserWithEmailAndPassword(emailOrPhone, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Toast.makeText(SignupActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();

                            // Get the user's unique ID (UID)
                            String userId = mAuth.getCurrentUser().getUid();

                            // Reference to the location in the Firestore where you want to store the user's information
                            DocumentReference userRef = db.collection("users").document(userId);

                            // Create a User object or simply a Map with the user's information
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", userName);
                            user.put("emailOrPhone", emailOrPhone);
                            user.put("city", city);

                            // Write the user's information to Firestore
                            userRef.set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        // User information saved successfully
                                        // Optionally, navigate to another activity or perform additional actions
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error occurred while saving user information
                                        Toast.makeText(SignupActivity.this, "Failed to save user information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
