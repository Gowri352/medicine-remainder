package com.example.medicinereminder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class MedicineAdapter
        extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private List<Map<String, Object>> medicineList;
    private List<String> medicineIds;

    FirebaseFirestore db;

    public MedicineAdapter(
            List<Map<String, Object>> medicineList,
            List<String> medicineIds) {

        this.medicineList = medicineList;
        this.medicineIds = medicineIds;

        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_item, parent, false);

        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MedicineViewHolder holder,
            int position) {

        Map<String, Object> medicine =
                medicineList.get(position);

        holder.medicineName.setText(
                "Medicine: " + medicine.get("medicineName")
        );

        holder.dosage.setText(
                "Dosage: " + medicine.get("dosage")
        );

        holder.time.setText(
                "Time: " + medicine.get("time")
        );

        holder.notes.setText(
                "Notes: " + medicine.get("notes")
        );

        // DELETE MEDICINE
        holder.deleteButton.setOnClickListener(v -> {

            int currentPosition =
                    holder.getAdapterPosition();

            if (currentPosition == RecyclerView.NO_POSITION) {
                return;
            }

            String medicineId =
                    medicineIds.get(currentPosition);

            db.collection("medicines")
                    .document(medicineId)
                    .delete()
                    .addOnSuccessListener(unused -> {

                        // Cancel the medicine alarm
                        AlarmHelper.cancelAlarm(
                                v.getContext(),
                                medicineId
                        );

                        medicineList.remove(currentPosition);
                        medicineIds.remove(currentPosition);

                        notifyItemRemoved(currentPosition);

                        Toast.makeText(
                                v.getContext(),
                                "Medicine deleted",
                                Toast.LENGTH_SHORT
                        ).show();
                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                v.getContext(),
                                "Delete failed: "
                                        + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });

        // EDIT MEDICINE
        holder.editButton.setOnClickListener(v -> {

            int currentPosition =
                    holder.getAdapterPosition();

            if (currentPosition == RecyclerView.NO_POSITION) {
                return;
            }

            String medicineId =
                    medicineIds.get(currentPosition);

            Map<String, Object> currentMedicine =
                    medicineList.get(currentPosition);

            Intent intent =
                    new Intent(
                            v.getContext(),
                            EditMedicineActivity.class
                    );

            intent.putExtra(
                    "medicineId",
                    medicineId
            );

            intent.putExtra(
                    "medicineName",
                    String.valueOf(
                            currentMedicine.get("medicineName")
                    )
            );

            intent.putExtra(
                    "dosage",
                    String.valueOf(
                            currentMedicine.get("dosage")
                    )
            );

            intent.putExtra(
                    "time",
                    String.valueOf(
                            currentMedicine.get("time")
                    )
            );

            intent.putExtra(
                    "notes",
                    String.valueOf(
                            currentMedicine.get("notes")
                    )
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class MedicineViewHolder
            extends RecyclerView.ViewHolder {

        TextView medicineName;
        TextView dosage;
        TextView time;
        TextView notes;

        Button editButton;
        Button deleteButton;

        public MedicineViewHolder(
                @NonNull View itemView) {

            super(itemView);

            medicineName =
                    itemView.findViewById(
                            R.id.itemMedicineName
                    );

            dosage =
                    itemView.findViewById(
                            R.id.itemDosage
                    );

            time =
                    itemView.findViewById(
                            R.id.itemTime
                    );

            notes =
                    itemView.findViewById(
                            R.id.itemNotes
                    );

            editButton =
                    itemView.findViewById(
                            R.id.editMedicineButton
                    );

            deleteButton =
                    itemView.findViewById(
                            R.id.deleteMedicineButton
                    );
        }
    }
}