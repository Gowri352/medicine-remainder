package com.example.medicinereminder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends Activity {

    MediaPlayer mediaPlayer;

    TextView medicineNameText;
    TextView dosageText;
    Button stopAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show alarm even when phone is locked
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        setContentView(R.layout.activity_alarm);

        medicineNameText =
                findViewById(R.id.alarmMedicineName);

        dosageText =
                findViewById(R.id.alarmDosage);

        stopAlarmButton =
                findViewById(R.id.stopAlarmButton);

        String medicineName =
                getIntent().getStringExtra("medicineName");

        String dosage =
                getIntent().getStringExtra("dosage");

        medicineNameText.setText(
                medicineName
        );

        dosageText.setText(
                "Dosage: " + dosage
        );

        // Start alarm sound
        startAlarmSound();

        // Stop alarm
        stopAlarmButton.setOnClickListener(v -> {

            stopAlarmSound();

            finish();
        });
    }

    private void startAlarmSound() {

        Uri alarmUri =
                RingtoneManager.getDefaultUri(
                        RingtoneManager.TYPE_ALARM
                );

        if (alarmUri == null) {

            alarmUri =
                    RingtoneManager.getDefaultUri(
                            RingtoneManager.TYPE_NOTIFICATION
                    );
        }

        mediaPlayer =
                MediaPlayer.create(
                        this,
                        alarmUri
                );

        if (mediaPlayer != null) {

            mediaPlayer.setLooping(true);

            mediaPlayer.start();
        }
    }

    private void stopAlarmSound() {

        if (mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {

        stopAlarmSound();

        super.onDestroy();
    }
}