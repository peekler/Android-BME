package demo.android.app.hu.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Peter on 2014.10.16..
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        Object[] pdus = (Object[]) extras.get("pdus");
        for (Object pdu : pdus) {
            SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdu);
            String origin = msg.getOriginatingAddress();
            String body = msg.getMessageBody();
            Toast.makeText(context, "SMS catched, number: " + origin +
                    " body: " + body, Toast.LENGTH_LONG).show();
        }
    }
}
