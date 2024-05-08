package com.example.delivery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPhoneNumber, editTextPassword, editTextConfirmPassword, editTextUserName, editTextCity;
    private Button buttonSignUp;
    private RadioGroup radioGroupOptions;
    private FirebaseAuth mAuth;
    private boolean isEmailSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextCity = findViewById(R.id.editTextCity);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);

        // Set default visibility for email and phone number fields
        editTextEmail.setVisibility(View.VISIBLE);
        editTextPhoneNumber.setVisibility(View.GONE);

        // Set OnClickListener for SignUp button
        buttonSignUp.setOnClickListener(view -> signUp());

        // Set OnCheckedChangeListener for radio group
        radioGroupOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonEmail) {
                    isEmailSelected = true;
                    editTextEmail.setVisibility(View.VISIBLE);
                    editTextPhoneNumber.setVisibility(View.GONE);
                } else if (checkedId == R.id.radioButtonPhoneNumber) {
                    isEmailSelected = false;
                    editTextEmail.setVisibility(View.GONE);
                    editTextPhoneNumber.setVisibility(View.VISIBLE);
                }
            }
    });
    }

    private void signUp() {
        String email = editTextEmail.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String userName = editTextUserName.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();

        String signUpCredential = isEmailSelected ? email : phoneNumber;

        // Perform validation
        if (TextUtils.isEmpty(signUpCredential)) {
            Toast.makeText(this, isEmailSelected ? "Enter email" : "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform sign up with email or phone number
        mAuth.createUserWithEmailAndPassword(signUpCredential, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Toast.makeText(SignupActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            finish(); // Close SignUpActivity after successful sign up
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
