package demo.android.app.hu.intentservicedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MyActivity extends Activity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == RESULT_OK) {
                Toast.makeText(
                        getApplicationContext(),
                        "Megjött a válasz!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Messenger myMessenger = new Messenger(handler);
                Intent i = new Intent(MyActivity.this,MyIntentService.class);
                i.putExtra(MyIntentService.KEY_MESSENGER, myMessenger);
                startService(i);
            }
        });
    }

}
