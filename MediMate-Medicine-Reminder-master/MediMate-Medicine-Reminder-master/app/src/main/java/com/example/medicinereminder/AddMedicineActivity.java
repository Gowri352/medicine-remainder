package com.example.medicinereminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

public class AddMedicineActivity extends AppCompatActivity {

    EditText medicineName;
    EditText dosage;
    EditText notes;

    Button selectTimeButton;
    Button saveMedicineButton;

    FirebaseFirestore db;
    FirebaseAuth auth;

    int selectedHour = -1;
    int selectedMinute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_medicine);

        medicineName =
                findViewById(R.id.medicineName);

        dosage =
                findViewById(R.id.dosage);

        notes =
                findViewById(R.id.notes);

        selectTimeButton =
                findViewById(R.id.selectTimeButton);

        saveMedicineButton =
                findViewById(R.id.saveMedicineButton);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        // SELECT MEDICINE TIME
        selectTimeButton.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            int currentHour =
                    calendar.get(Calendar.HOUR_OF_DAY);

            int currentMinute =
                    calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog =
                    new TimePickerDialog(
                            AddMedicineActivity.this,

                            (view, hourOfDay, minute) -> {

                                selectedHour = hourOfDay;
                                selectedMinute = minute;

                                String amPm;

                                if (hourOfDay >= 12) {
                                    amPm = "PM";
                                } else {
                                    amPm = "AM";
                                }

                                int displayHour =
                                        hourOfDay % 12;

                                if (displayHour == 0) {
                                    displayHour = 12;
                                }

                                String selectedTime =
                                        String.format(
                                                Locale.getDefault(),
                                                "%02d:%02d %s",
                                                displayHour,
                                                minute,
                                                amPm
                                        );

                                selectTimeButton.setText(
                                        selectedTime
                                );
                            },

                            currentHour,
                            currentMinute,
                            false
                    );

            timePickerDialog.show();
        });


        // SAVE MEDICINE
        saveMedicineButton.setOnClickListener(v -> {

            String name =
                    medicineName.getText()
                            .toString()
                            .trim();

            String dose =
                    dosage.getText()
                            .toString()
                            .trim();

            String note =
                    notes.getText()
                            .toString()
                            .trim();


            // Check required fields
            if (name.isEmpty()
                    || dose.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please fill all required fields",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            // Check time
            if (selectedHour == -1
                    || selectedMinute == -1) {

                Toast.makeText(
                        this,
                        "Please select a medicine time",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            // Check login
            if (auth.getCurrentUser() == null) {

                Toast.makeText(
                        this,
                        "Please login first",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            String userId =
                    auth.getCurrentUser().getUid();


            // Convert time to AM/PM
            String amPm;

            if (selectedHour >= 12) {
                amPm = "PM";
            } else {
                amPm = "AM";
            }

            int displayHour =
                    selectedHour % 12;

            if (displayHour == 0) {
                displayHour = 12;
            }

            String selectedTime =
                    String.format(
                            Locale.getDefault(),
                            "%02d:%02d %s",
                            displayHour,
                            selectedMinute,
                            amPm
                    );


            // Medicine data
            Map<String, Object> medicine =
                    new HashMap<>();

            medicine.put(
                    "medicineName",
                    name
            );

            medicine.put(
                    "dosage",
                    dose
            );

            medicine.put(
                    "time",
                    selectedTime
            );

            medicine.put(
                    "hour",
                    selectedHour
            );

            medicine.put(
                    "minute",
                    selectedMinute
            );

            medicine.put(
                    "notes",
                    note
            );

            medicine.put(
                    "userId",
                    userId
            );


            // Save to Firestore
            db.collection("medicines")
                    .add(medicine)
                    .addOnSuccessListener(
                            documentReference -> {

                                String medicineId =
                                        documentReference.getId();


                                // Schedule alarm
                                AlarmHelper.scheduleAlarm(
                                        this,
                                        medicineId,
                                        name,
                                        dose,
                                        selectedHour,
                                        selectedMinute
                                );


                                Toast.makeText(
                                        this,
                                        "Medicine saved and reminder set!",
                                        Toast.LENGTH_SHORT
                                ).show();


                                finish();
                            })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                this,
                                "Failed: "
                                        + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });
    }
}