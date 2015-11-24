package hu.rik.android.servicedemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final String KEY_MESSENGER = "KEY_MESSENGER";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == RESULT_OK) {
                String status = (String) msg.obj;
                Toast.makeText(MainActivity.this, status,
                        Toast.LENGTH_SHORT).show();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*Intent i = new Intent(MainActivity.this, IntentServiceDemo.class);
                Messenger messenger = new Messenger(handler);
                i.putExtra(KEY_MESSENGER, messenger);
                startService(i);*/

                /*Intent intent = new Intent(
                        MainActivity.this,
                        StartedService.class);
                startService(intent);*/

                Intent intent = new Intent(
                        MainActivity.this,
                        MyTimeServiceWindow.class);
                startService(intent);
            }
        });
        Button btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*Intent intent = new Intent(
                        MainActivity.this,
                        StartedService.class);
                stopService(intent);*/

                Intent intent = new Intent(
                        MainActivity.this,
                        MyTimeServiceWindow.class);
                stopService(intent);
            }
        });
    }

}
