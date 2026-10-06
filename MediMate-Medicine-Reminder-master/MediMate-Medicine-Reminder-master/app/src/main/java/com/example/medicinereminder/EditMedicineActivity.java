package com.example.medicinereminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Locale;

public class EditMedicineActivity extends AppCompatActivity {

    EditText medicineName;
    EditText dosage;
    EditText notes;

    Button selectTimeButton;
    Button updateMedicineButton;

    FirebaseFirestore db;

    String medicineId;

    int selectedHour = -1;
    int selectedMinute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_medicine);

        medicineName =
                findViewById(R.id.editMedicineName);

        dosage =
                findViewById(R.id.editDosage);

        notes =
                findViewById(R.id.editNotes);

        selectTimeButton =
                findViewById(R.id.editSelectTimeButton);

        updateMedicineButton =
                findViewById(R.id.updateMedicineButton);

        db = FirebaseFirestore.getInstance();

        // Get medicine information
        medicineId =
                getIntent().getStringExtra("medicineId");

        String name =
                getIntent().getStringExtra("medicineName");

        String dose =
                getIntent().getStringExtra("dosage");

        String time =
                getIntent().getStringExtra("time");

        String note =
                getIntent().getStringExtra("notes");

        // Show existing values
        medicineName.setText(name);
        dosage.setText(dose);
        notes.setText(note);

        if (time != null && !time.isEmpty()) {
            selectTimeButton.setText(time);
        }

        // Convert existing time to hour/minute
        if (time != null && !time.isEmpty()) {

            try {

                String[] parts =
                        time.split(" ");

                String[] timeParts =
                        parts[0].split(":");

                int hour =
                        Integer.parseInt(timeParts[0]);

                int minute =
                        Integer.parseInt(timeParts[1]);

                String amPm =
                        parts[1];

                if (amPm.equals("PM") && hour != 12) {
                    hour += 12;
                }

                if (amPm.equals("AM") && hour == 12) {
                    hour = 0;
                }

                selectedHour = hour;
                selectedMinute = minute;

            } catch (Exception e) {

                selectedHour = -1;
                selectedMinute = -1;
            }
        }

        // Select new time
        selectTimeButton.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            int currentHour =
                    calendar.get(Calendar.HOUR_OF_DAY);

            int currentMinute =
                    calendar.get(Calendar.MINUTE);

            if (selectedHour != -1) {
                currentHour = selectedHour;
            }

            if (selectedMinute != -1) {
                currentMinute = selectedMinute;
            }

            TimePickerDialog dialog =
                    new TimePickerDialog(
                            EditMedicineActivity.this,

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

            dialog.show();
        });

        // Update medicine
        updateMedicineButton.setOnClickListener(v -> {

            String newName =
                    medicineName.getText()
                            .toString()
                            .trim();

            String newDosage =
                    dosage.getText()
                            .toString()
                            .trim();

            String newNotes =
                    notes.getText()
                            .toString()
                            .trim();

            // Check required fields
            if (newName.isEmpty()
                    || newDosage.isEmpty()) {

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

            String newTime =
                    String.format(
                            Locale.getDefault(),
                            "%02d:%02d %s",
                            displayHour,
                            selectedMinute,
                            amPm
                    );

            // Update Firestore
            db.collection("medicines")
                    .document(medicineId)
                    .update(
                            "medicineName", newName,
                            "dosage", newDosage,
                            "time", newTime,
                            "hour", selectedHour,
                            "minute", selectedMinute,
                            "notes", newNotes
                    )
                    .addOnSuccessListener(unused -> {

                        // Cancel old alarm
                        AlarmHelper.cancelAlarm(
                                this,
                                medicineId
                        );

                        // Schedule new alarm
                        AlarmHelper.scheduleAlarm(
                                this,
                                medicineId,
                                newName,
                                newDosage,
                                selectedHour,
                                selectedMinute
                        );

                        Toast.makeText(
                                this,
                                "Medicine updated successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                this,
                                "Update failed: "
                                        + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });
    }
}