package demo.android.app.hu.startedservicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Peter on 2014.10.21..
 */
public class MyTimeService extends Service {

    private final int NOTIF_FOREGROUND_ID = 101;
    private boolean enabled = false;
    private MyTimeThread myTimeThread = null;

    private class MyTimeThread extends Thread {
        public void run() {
            Handler h = new Handler(MyTimeService.this.getMainLooper());

            while (enabled) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyTimeService.this,
                                new Date(System.currentTimeMillis()).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

                updateNotification(
                        new Date(System.currentTimeMillis()).toString());

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIF_FOREGROUND_ID,
                getMyNotification("starting..."));
        enabled = true;
        if (myTimeThread == null) {
            myTimeThread = new MyTimeThread();
            myTimeThread.start();
        }
        return START_STICKY;
    }

    private void updateNotification(String text) {
        Notification notification = getMyNotification(text);
        NotificationManager notifMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifMan.notify(NOTIF_FOREGROUND_ID,notification);
    }

    private Notification getMyNotification(String text) {
        Intent notificationIntent = new Intent(this, MyActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                NOTIF_FOREGROUND_ID,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("This the MyTimeService")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher)
                .setVibrate(new long[]{1000,2000,1000})
                .setContentIntent(contentIntent).build();
        return  notification;
    }


    @Override
    public void onDestroy() {
        stopForeground(true);
        enabled = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
