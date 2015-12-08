package demo.android.app.hu.nfcreadwritetest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends Activity {

    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;
    private IntentFilter[] mWriteTagFilters;
    private EditText etNfcToWrite;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        etNfcToWrite = (EditText) findViewById(R.id.etNfcToWrite);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        Button btnEnableWrite = (Button) findViewById(R.id.btnEnableWrite);
        btnEnableWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableTagWriteMode();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            NdefMessage[] msgs = null;

            Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }

            if (msgs != null) {
                for (NdefMessage tmpMsg : msgs) {
                    for (NdefRecord tmpRecord : tmpMsg.getRecords()) {
                        tvStatus.append("\n"+new String(tmpRecord.getPayload()));
                    }
                }
            }
        }

    }

    private void enableTagWriteMode() {
        IntentFilter tagDetected = new IntentFilter(
                NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] { tagDetected };
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
                mWriteTagFilters, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Tag writing mode
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NdefRecord record1 =
                    createTextRecord(etNfcToWrite.getText().toString());

            NdefRecord record2 =
                    NdefRecord.createUri("sms:+36301234567?body=Hello App! NFC!");

            NdefRecord record3 =
                    NdefRecord.createUri("tel:+36301234567");

            NdefRecord record4 =
                    NdefRecord.createUri("http://hwsw.hu");

            NdefMessage msg = new NdefMessage(
                    new NdefRecord[] { record1, record2, record3, record4 });

            if (writeTag(msg, detectedTag)) {
                Toast.makeText(this, "Success write operation!", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(this, "Failed to write!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public NdefRecord createTextRecord(String payload) {
        byte[] textBytes = payload.getBytes();
        byte[] data = new byte[1 + textBytes.length];
        data[0] = (byte) 0;
        System.arraycopy(textBytes, 0, data, 1,	textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }

    public static boolean writeTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    return false;
                }
                ndef.writeNdefMessage(message);
                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
}