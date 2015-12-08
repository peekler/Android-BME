package com.example.contentproviderdemo;

import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String KEY_LOG = "TEST";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnGet = (Button) findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Cursor c = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, 
								ContactsContract.CommonDataKinds.Phone.NUMBER},
						ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" LIKE '%Tamás%'",
						//null,
						null,
						ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " DESC");
				
				//Toast.makeText(MainActivity.this, ""+c.getCount(), Toast.LENGTH_LONG).show();

				while (c.moveToNext()) {
					String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					Log.d(KEY_LOG, name);
					Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
				}
			}
		});
        Button btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ContentResolver cr = getContentResolver();
				ContentValues values = new ContentValues();
				values.put(CalendarContract.Events.DTSTART, System.currentTimeMillis());
				values.put(CalendarContract.Events.DTEND, System.currentTimeMillis()+60000);
				values.put(CalendarContract.Events.TITLE, "Frappánsabb");
				values.put(CalendarContract.Events.DESCRIPTION, "Demo");
				values.put(CalendarContract.Events.CALENDAR_ID, 1);
				values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
				Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
								
				//getContentResolver().delete(CalendarContract.Events.CONTENT_URI, CalendarContract.Events._ID+"=599", null);
				
				Log.d(KEY_LOG,uri.toString());
				//showCallLog();
			}
		});
        
    }
    
    private void showCallLog() {
    	String[] strFields = { android.provider.CallLog.Calls._ID,
				android.provider.CallLog.Calls.NUMBER,
				android.provider.CallLog.Calls.CACHED_NAME, };
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
		final Cursor cursorCall = getContentResolver().query(
				android.provider.CallLog.Calls.CONTENT_URI, strFields,
				null, null, strOrder);

		AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity.this);
		builder.setTitle("Select recent contact");
		android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface,
					int item) {
				cursorCall.moveToPosition(item);
				Toast.makeText(
						MainActivity.this,
						cursorCall.getString(cursorCall
								.getColumnIndex(android.provider.CallLog.Calls.NUMBER)),
						Toast.LENGTH_LONG).show();
				cursorCall.close();
				return;
			}
		};
		builder.setCursor(cursorCall, listener,
				android.provider.CallLog.Calls.CACHED_NAME);
		builder.create().show();
    }
}
