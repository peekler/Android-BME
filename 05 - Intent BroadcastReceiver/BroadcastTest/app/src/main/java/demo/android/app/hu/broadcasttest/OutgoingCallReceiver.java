package demo.android.app.hu.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Peter on 2014.10.16..
 */
public class OutgoingCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //TODO: Handle outgoing call event here


        final String originalNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Toast.makeText(context, "Outgoing call catched! " + originalNumber, Toast.LENGTH_LONG).show();
        this.setResultData("0123456789");
    }
}