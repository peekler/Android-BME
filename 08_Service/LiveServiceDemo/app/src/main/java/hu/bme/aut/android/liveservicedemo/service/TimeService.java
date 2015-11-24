package hu.bme.aut.android.liveservicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Peter on 2015.03.31..
 */
public class TimeService extends Service {

  private class MyTimeThread extends Thread {
    public void run() {
      Handler h = new Handler(TimeService.this.getMainLooper());

      while(enabled) {
        h.post(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(TimeService.this, new Date(
                    System.currentTimeMillis()).toString(), Toast.LENGTH_SHORT).show();
          }
        });

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
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    enabled = true;
    new MyTimeThread().start();
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    enabled = false;
    super.onDestroy();
  }
}
