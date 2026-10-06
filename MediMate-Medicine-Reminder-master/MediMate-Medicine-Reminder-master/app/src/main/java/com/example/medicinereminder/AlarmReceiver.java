package com.example.medicinereminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {

        String medicineId =
                intent.getStringExtra("medicineId");

        String medicineName =
                intent.getStringExtra("medicineName");

        String dosage =
                intent.getStringExtra("dosage");

        int hour =
                intent.getIntExtra("hour", -1);

        int minute =
                intent.getIntExtra("minute", -1);

        // Open the alarm screen
        Intent alarmIntent =
                new Intent(
                        context,
                        AlarmActivity.class
                );

        alarmIntent.putExtra(
                "medicineName",
                medicineName
        );

        alarmIntent.putExtra(
                "dosage",
                dosage
        );

        alarmIntent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
        );

        context.startActivity(alarmIntent);

        // Schedule tomorrow's alarm
        if (medicineId != null
                && hour != -1
                && minute != -1) {

            scheduleNextAlarm(
                    context,
                    medicineId,
                    medicineName,
                    dosage,
                    hour,
                    minute
            );
        }
    }

    private void scheduleNextAlarm(
            Context context,
            String medicineId,
            String medicineName,
            String dosage,
            int hour,
            int minute
    ) {

        AlarmManager alarmManager =
                (AlarmManager)
                        context.getSystemService(
                                Context.ALARM_SERVICE
                        );

        Calendar nextAlarm =
                Calendar.getInstance();

        nextAlarm.add(
                Calendar.DAY_OF_YEAR,
                1
        );

        nextAlarm.set(
                Calendar.HOUR_OF_DAY,
                hour
        );

        nextAlarm.set(
                Calendar.MINUTE,
                minute
        );

        nextAlarm.set(
                Calendar.SECOND,
                0
        );

        nextAlarm.set(
                Calendar.MILLISECOND,
                0
        );

        Intent intent =
                new Intent(
                        context,
                        AlarmReceiver.class
                );

        intent.putExtra(
                "medicineId",
                medicineId
        );

        intent.putExtra(
                "medicineName",
                medicineName
        );

        intent.putExtra(
                "dosage",
                dosage
        );

        intent.putExtra(
                "hour",
                hour
        );

        intent.putExtra(
                "minute",
                minute
        );

        int requestCode =
                medicineId.hashCode();

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        // Check exact alarm permission
        if (Build.VERSION.SDK_INT >= 31) {

            if (!alarmManager.canScheduleExactAlarms()) {
                return;
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextAlarm.getTimeInMillis(),
                pendingIntent
        );
    }
}