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

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import hu.bme.aut.android.moneyconverter.data.MoneyResult;


public class MainActivity extends ActionBarActivity {

    private final String URL_BASE =
            "http://api.fixer.io/latest?base=USD";

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MoneyResult moneyResult) {
        tvResult.setText("1 USD is " +
                moneyResult.getRates().getHUF() +
                " HUF");
    }
}
