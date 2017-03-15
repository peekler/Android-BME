package hu.bme.aut.sharedprefdemo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_START = "KEY_START";
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);

        showLastStart();

        saveLastStart();


    }

    private void saveLastStart() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_START, new Date(System.currentTimeMillis()).toString());
        edit.commit();
    }

    private void showLastStart() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String start = sp.getString(KEY_START, "NEVER");

        tvStatus.setText(start);
    }


}
