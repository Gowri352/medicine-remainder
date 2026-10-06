package com.example.medicinereminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmHelper {

    // Schedule medicine alarm
    public static void scheduleAlarm(
            Context context,
            String medicineId,
            String medicineName,
            String dosage,
            int hour,
            int minute) {

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(
                        Context.ALARM_SERVICE
                );

        // Android 12+ exact alarm permission
        if (android.os.Build.VERSION.SDK_INT >= 31) {

            if (!alarmManager.canScheduleExactAlarms()) {

                Intent settingsIntent =
                        new Intent(
                                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        );

                settingsIntent.setData(
                        Uri.parse(
                                "package:" +
                                        context.getPackageName()
                        )
                );

                context.startActivity(settingsIntent);

                Toast.makeText(
                        context,
                        "Please allow exact alarms for MediMate",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }
        }

        Intent intent =
                new Intent(context, AlarmReceiver.class);

        intent.putExtra("medicineId", medicineId);
        intent.putExtra("medicineName", medicineName);
        intent.putExtra("dosage", dosage);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);

        int requestCode = medicineId.hashCode();

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        Calendar alarmTime =
                Calendar.getInstance();

        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);
        alarmTime.set(Calendar.MILLISECOND, 0);

        // If today's time has already passed,
        // schedule for tomorrow
        if (alarmTime.getTimeInMillis()
                <= System.currentTimeMillis()) {

            alarmTime.add(
                    Calendar.DAY_OF_YEAR,
                    1
            );
        }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime.getTimeInMillis(),
                pendingIntent
        );
    }


    // Cancel medicine alarm
    public static void cancelAlarm(
            Context context,
            String medicineId) {

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(
                        Context.ALARM_SERVICE
                );

        Intent intent =
                new Intent(context, AlarmReceiver.class);

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

        alarmManager.cancel(pendingIntent);

        pendingIntent.cancel();
    }
}