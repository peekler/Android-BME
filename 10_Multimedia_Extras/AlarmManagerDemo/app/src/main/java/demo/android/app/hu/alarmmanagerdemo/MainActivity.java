package demo.android.app.hu.alarmmanagerdemo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends Activity implements MediaPlayer.OnPreparedListener{

    public static final String KEY_WAKEUP ="key_wakeup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        WakeUpAlarmReceiver.class);
                intent.putExtra("key","test data");
                PendingIntent sender = PendingIntent.getBroadcast(
                        MainActivity.this, 0, intent, 0
                );

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 10);

                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        10000,
                        sender);
            }
        });

        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WakeUpAlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,
                        0, i, 0);
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.cancel(pi);
            }
        });


        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getInt(KEY_WAKEUP) == 1) {
                playWakeUp();
            }
        }
    }

    private void playWakeUp() {
        try {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.wakeup);
            mp.setOnPreparedListener(this);
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
