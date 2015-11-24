package hu.rik.android.weatherreport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity {

    public static final String FILTER_WEATHER = "FILTER_WEATHER";
    public static final String KEY_RESULT = "KEY_RESULT";
    private TextView tvResult;
    private ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        Button btnGetWeather = (Button) findViewById(R.id.btnGetWeather);
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeather(etCity.getText().toString());
            }
        });
    }

    private void loadWeather(String cityName) {
        String encCity = cityName;
        try {
            encCity = URLEncoder.encode(cityName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        new AsyncTaskHttpGet(getApplicationContext()).execute(
            "http://api.openweathermap.org/data/2.5/weather?q="
                +encCity+"&units=metrics&appid=2de143494c0b295cca9337e1e96b00e0"
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(
           brWeather, new IntentFilter(FILTER_WEATHER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
            brWeather);
    }

    private BroadcastReceiver brWeather = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(KEY_RESULT);
            try {
                String desc =
                        ((JSONObject)new JSONObject(result).getJSONArray(
                                "weather").get(0)).getString("description");
                tvResult.setText(desc);

                String iconName =
                        ((JSONObject)new JSONObject(result).getJSONArray(
                                "weather").get(0)).getString("icon");
                //http://openweathermap.org/img/w/01d.png
                Glide.with(MainActivity.this).load(
                        "http://openweathermap.org/img/w/"+iconName+".png"
                ).into(ivIcon);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}
