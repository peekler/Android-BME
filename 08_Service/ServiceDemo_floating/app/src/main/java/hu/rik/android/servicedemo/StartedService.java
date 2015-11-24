package hu.rik.android.servicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Peter on 2015.01.14..
 */
public class StartedService extends Service {

    private static final int NOTIF_ID = 101;

    private class MyTimeThread extends Thread {
        public void run() {

            Handler h = new Handler(
                    StartedService.this.getMainLooper());

            while(enabled) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StartedService.this,
                                new Date(System.currentTimeMillis()).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

                updateNotfication(
                        new Date(System.currentTimeMillis()).toString());

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean enabled = false;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIF_ID, getMyNotification("hello"));

        enabled = true;
        new MyTimeThread().start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        enabled = false;
        super.onDestroy();
    }

    private Notification getMyNotification(String msg) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                NOTIF_ID, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notif = new Notification.Builder(this).
                setContentTitle("Title").
                setContentText(msg).
                setSmallIcon(R.drawable.ic_launcher).
                setContentIntent(contentIntent).
                build();

        return notif;
    }

    private void updateNotfication(String msg) {
        Notification notif = getMyNotification(msg);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIF_ID, notif);
    }
}
