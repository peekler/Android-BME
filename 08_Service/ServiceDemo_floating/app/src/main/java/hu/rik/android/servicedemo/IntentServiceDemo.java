package hu.rik.android.servicedemo;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;

/**
 * Created by Peter on 2015.01.14..
 */
public class IntentServiceDemo extends IntentService {

    public IntentServiceDemo() {
        super("IntentServiceDemo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Bundle extras = intent.getExtras();
        if (extras != null) {
            Messenger messenger = (Messenger) extras.get(MainActivity.KEY_MESSENGER);
            Message msg = Message.obtain();
            msg.arg1 = Activity.RESULT_OK;
            msg.obj = "LEFUTOTT a SERIVCE";
            try {
                messenger.send(msg);
            } catch (android.os.RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }
}
