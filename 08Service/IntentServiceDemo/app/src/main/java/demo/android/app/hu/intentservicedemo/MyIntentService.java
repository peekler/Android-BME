package demo.android.app.hu.intentservicedemo;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Peter on 2014.10.21..
 */
public class MyIntentService extends IntentService {

    public static final String KEY_MESSENGER = "KEY_MESSENGER";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // ide jön a hosszan tartú művelet, pl letöltés
        //....
        //"http://test.hu/test.dat"
        //String url = intent.getStringExtra("KEY_URL");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bundle extras = intent.getExtras();
        if (extras != null) {
            Messenger messenger = (Messenger) extras.get(KEY_MESSENGER);
            Message msg = Message.obtain();
            msg.arg1 = Activity.RESULT_OK;
            //msg.obj = bármilyen objektumot átadhatunk
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
