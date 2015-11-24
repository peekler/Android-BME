package hu.bme.aut.android.moneyconverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private final String URL_BASE =
            "http://api.fixer.io/latest?base=USD&symbols=HUF";

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        Button btnGetRate = (Button) findViewById(R.id.btnGetRate);
        btnGetRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = URL_BASE;

                new HttpGetTask(getApplicationContext()).
                        execute(query);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                brWeatherReceiver,
                new IntentFilter(HttpGetTask.FILTER_RESULT)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(
                this).unregisterReceiver(brWeatherReceiver);
    }

    private BroadcastReceiver brWeatherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String rawResult = intent.getStringExtra(
                    HttpGetTask.KEY_RESULT);

            try {
                JSONObject rawJson = new JSONObject(rawResult);
                String hufValue =rawJson.getJSONObject("rates").getString("HUF");
                tvResult.setText(hufValue);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };


}
