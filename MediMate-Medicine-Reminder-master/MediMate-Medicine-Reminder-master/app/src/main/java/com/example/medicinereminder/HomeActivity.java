package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button addMedicineButton;
    Button myMedicinesButton;
    Button profileButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        // Find buttons
        addMedicineButton =
                findViewById(R.id.addMedicineButton);

        myMedicinesButton =
                findViewById(R.id.myMedicinesButton);

        profileButton =
                findViewById(R.id.profileButton);

        logoutButton =
                findViewById(R.id.logoutButton);


        // Add Medicine button
        addMedicineButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            AddMedicineActivity.class
                    );

            startActivity(intent);
        });


        // My Medicines button
        myMedicinesButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            MyMedicinesActivity.class
                    );

            startActivity(intent);
        });


        // Profile button
        profileButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            ProfileActivity.class
                    );

            startActivity(intent);
        });


        // Logout button
        logoutButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
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