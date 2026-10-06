package com.example.medicinereminder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button loginButton;
    TextView registerText;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ask notification permission for Android 13+
        if (android.os.Build.VERSION.SDK_INT >= 33) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100
                );
            }
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.registerText);

        auth = FirebaseAuth.getInstance();

        // Login
        loginButton.setOnClickListener(v -> {

            String userEmail =
                    email.getText().toString().trim();

            String userPassword =
                    password.getText().toString().trim();

            if (userEmail.isEmpty() || userPassword.isEmpty()) {

                Toast.makeText(
                        this,
                        "Enter email and password",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            auth.signInWithEmailAndPassword(
                    userEmail,
                    userPassword
            ).addOnCompleteListener(this, task -> {

                if (task.isSuccessful()) {

                    Toast.makeText(
                            this,
                            "Login successful",
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent intent = new Intent(
                            MainActivity.this,
                            HomeActivity.class
                    );

                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(
                            this,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });

        // Open Register screen
        registerText.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);
        });
    }
}