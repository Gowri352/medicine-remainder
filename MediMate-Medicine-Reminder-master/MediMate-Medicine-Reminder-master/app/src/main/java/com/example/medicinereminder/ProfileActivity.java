package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    TextView profileEmail;
    Button profileLogoutButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        profileEmail = findViewById(R.id.profileEmail);
        profileLogoutButton =
                findViewById(R.id.profileLogoutButton);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            profileEmail.setText(
                    "Email: " +
                            auth.getCurrentUser().getEmail()
            );
        }

        profileLogoutButton.setOnClickListener(v -> {

            auth.signOut();

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            MainActivity.class
                    );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();
        });
    }
}