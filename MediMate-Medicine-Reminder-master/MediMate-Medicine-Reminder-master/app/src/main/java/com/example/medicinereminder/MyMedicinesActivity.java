package com.example.medicinereminder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyMedicinesActivity extends AppCompatActivity {

    RecyclerView medicineRecyclerView;
    TextView noMedicineText;

    FirebaseFirestore db;
    FirebaseAuth auth;

    List<Map<String, Object>> medicineList;
    List<String> medicineIds;

    MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_medicines);

        medicineRecyclerView =
                findViewById(R.id.medicineRecyclerView);

        noMedicineText =
                findViewById(R.id.noMedicineText);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        medicineList = new ArrayList<>();
        medicineIds = new ArrayList<>();

        adapter = new MedicineAdapter(
                medicineList,
                medicineIds
        );

        medicineRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        medicineRecyclerView.setAdapter(adapter);

        loadMedicines();
    }

    private void loadMedicines() {

        if (auth.getCurrentUser() == null) {
            return;
        }

        String userId =
                auth.getCurrentUser().getUid();

        db.collection("medicines")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    medicineList.clear();
                    medicineIds.clear();

                    for (var document :
                            queryDocumentSnapshots.getDocuments()) {

                        medicineList.add(
                                document.getData()
                        );

                        medicineIds.add(
                                document.getId()
                        );
                    }

                    adapter.notifyDataSetChanged();

                    if (medicineList.isEmpty()) {

                        noMedicineText.setVisibility(
                                View.VISIBLE
                        );

                    } else {

                        noMedicineText.setVisibility(
                                View.GONE
                        );
                    }
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Failed: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            loadMedicines();
        }
    }
}