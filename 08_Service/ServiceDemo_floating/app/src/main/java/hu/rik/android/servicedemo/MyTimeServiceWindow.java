package hu.rik.android.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Peter on 2015.01.14..
 */
public class MyTimeServiceWindow extends Service {

    private boolean enabled = true;
    private WindowManager windowManager;
    private View floatingView;
    private TextView tvTime;

    private class MyTimeShower extends Thread {
        public void run() {
            Handler h = new Handler(MyTimeServiceWindow.this.getMainLooper());
            while (enabled) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText(new Date(System.currentTimeMillis()).toString());
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

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        floatingView = ((LayoutInflater) getSystemService(
                LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.float_view_layout, null);
        tvTime = (TextView) floatingView.findViewById(R.id.tvTime);
        tvTime.setText(new Date(System.currentTimeMillis()).toString());

        final WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(floatingView, params);

        try {
            floatingView.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(floatingView, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        enabled = false;
        if (floatingView != null) windowManager.removeView(floatingView);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        enabled = true;
        new MyTimeShower().start();
        return super.onStartCommand(intent, flags, startId);
    }
}
