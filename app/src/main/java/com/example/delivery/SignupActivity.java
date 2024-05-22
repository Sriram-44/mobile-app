package com.example.delivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private LottieAnimationView loadingAnimation;
    private MaterialButtonToggleGroup toggleGroup;
    private Toast toast = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        editTextName = findViewById(R.id.userName);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        toggleGroup = findViewById(R.id.loginToggleGroup);
        MaterialButton SignupButton = findViewById(R.id.signUpButton);
        MaterialButton loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationEmail();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sendVerificationEmail() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordConf = editTextConfirmPassword.getText().toString().trim();

        if (toast != null)
            toast.cancel();

        if (TextUtils.isEmpty(name)) {
            toast = Toast.makeText(SignupActivity.this, "Name cannot be empty",
                    Toast.LENGTH_SHORT);
            toast.show();
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            toast = Toast.makeText(SignupActivity.this, "Email cannot be empty",
                    Toast.LENGTH_SHORT);
            toast.show();
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            toast = Toast.makeText(SignupActivity.this, "Password cannot be empty",
                    Toast.LENGTH_SHORT);
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            toast.show();
            return;
        }

        if (TextUtils.isEmpty(passwordConf)) {
            toast = Toast.makeText(SignupActivity.this, "Password cannot be empty",
                    Toast.LENGTH_SHORT);
            editTextConfirmPassword.setError("Password is required");
            editTextConfirmPassword.requestFocus();
            toast.show();
            return;
        }

        if (!password.equals(passwordConf)) {
            toast = Toast.makeText(SignupActivity.this, "Password don't match",
                    Toast.LENGTH_SHORT);
            editTextPassword.setError("Passwords don't match");
            editTextConfirmPassword.setError("Passwords don't match");
            editTextPassword.requestFocus();
            editTextConfirmPassword.requestFocus();
            toast.show();
            return;
        }

        toggleGroup.setVisibility(View.GONE);
        loadingAnimation.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> verificationTask) {
                                            if (verificationTask.isSuccessful()) {
                                                if (toast != null)
                                                    toast.cancel();
                                                toast = Toast.makeText(SignupActivity.this, "Verification email sent", Toast.LENGTH_SHORT);
                                                toast.show();
                                                toggleGroup.setVisibility(View.GONE);
                                                loadingAnimation.setVisibility(View.VISIBLE);
                                                checkEmailVerificationStatus();
                                            } else {
                                                if (toast != null)
                                                    toast.cancel();
                                                toast = Toast.makeText(SignupActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT);
                                                toast.show();
                                                toggleGroup.setVisibility(View.VISIBLE);
                                                loadingAnimation.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            if (toast != null)
                                toast.cancel();
                            toast = Toast.makeText(SignupActivity.this, "Failed to create account. Please try again", Toast.LENGTH_SHORT);
                            toast.show();
                            toggleGroup.setVisibility(View.VISIBLE);
                            loadingAnimation.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void checkEmailVerificationStatus() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    user.reload();
                    if (user.isEmailVerified()) {
                        addUserToRealtimeDatabase(editTextName.getText().toString().trim(), editTextEmail.getText().toString().trim());
                    } else {
                        checkEmailVerificationStatus();
                    }
                }
            }
        }, 2000);
    }

    public void addUserToRealtimeDatabase(String name, String email) {
        String userId = mAuth.getCurrentUser().getUid();
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        dbRef.child("users").child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (toast != null)
                                toast.cancel();
                            toast = Toast.makeText(SignupActivity.this, "Sign up successful", Toast.LENGTH_SHORT);
                            toast.show();
                            Log.d(TAG, "User details added to Realtime Database");
                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            if (toast != null)
                                toast.cancel();
                            Log.w(TAG, "Error adding user details to Realtime Database", task.getException());
                            toast = Toast.makeText(SignupActivity.this, "Error adding user details to Realtime Database", Toast.LENGTH_SHORT);
                            toast.show();
                            toggleGroup.setVisibility(View.VISIBLE);
                            loadingAnimation.setVisibility(View.GONE);
                        }
                    }
                });

        dbRef.child("userSongs").child(userId).setValue(user);
        dbRef.child("userAlbums").child(userId).setValue(user);
        dbRef.child("userArtists").child(userId).setValue(user);
        dbRef.child("userPlaylists").child(userId).setValue(user);
    }

    @Override
    public void onBackPressed() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && !user.isEmailVerified()) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                toggleGroup.setVisibility(View.VISIBLE);
                                loadingAnimation.setVisibility(View.GONE);
                                if (toast != null)
                                    toast.cancel();
                                Log.d(TAG, "User deleted");
                                toast = Toast.makeText(SignupActivity.this, "Sign up canceled", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                if (toast != null)
                                    toast.cancel();
                                Log.w(TAG, "Error deleting user", task.getException());
                                toast = Toast.makeText(SignupActivity.this, "Clean up error", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
        } else {
            super.onBackPressed();
        }
    }
}
