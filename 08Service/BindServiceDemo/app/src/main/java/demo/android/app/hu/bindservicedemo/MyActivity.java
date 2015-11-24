package demo.android.app.hu.bindservicedemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends Activity {

    private MyBoundService service;
    private boolean serviceBounded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final TextView tvStat = (TextView) findViewById(R.id.tvStat);
        Button btnGetFreeSpace = (Button) findViewById(R.id.btnGetFreeSpace);
        btnGetFreeSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceBounded && service != null) {
                    long freeMB = service.getFreeSpace();
                    long freeGB = freeMB / 1024;

                    tvStat.setText(freeGB+" GB");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, MyBoundService.class);
        bindService(i, servConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(servConn);
    }

    private ServiceConnection servConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(
                ComponentName componentName, IBinder iBinder) {
            service = ((MyBoundService.MyLocalBinder) iBinder).getService();
            serviceBounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

          serviceBounded = false;
        }
    };
}
