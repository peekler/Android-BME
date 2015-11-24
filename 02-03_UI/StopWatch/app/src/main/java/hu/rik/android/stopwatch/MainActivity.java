package hu.rik.android.stopwatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    private LinearLayout layoutTimeStamps;
    private long startTime;
    LayoutInflater inflater = null;
    private Timer timer = null;
    private TextView tvElapsedTime = null;



    private class StopWatchTimerTask extends TimerTask {
        @Override
        public void run() {
            final int diff = (int)(System.currentTimeMillis() - startTime)/1000;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvElapsedTime.setText(String.valueOf(diff));
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutTimeStamps = (LinearLayout) findViewById(R.id.layoutTimeStamps);
        tvElapsedTime = (TextView) findViewById(R.id.tvElapsedTime);


        Button btnStart = (Button) findViewById(R.id.btnStart);
        final Button btnPart = (Button) findViewById(R.id.btnPart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutTimeStamps.removeAllViews();
                startTime = System.currentTimeMillis();
                btnPart.setEnabled(true);
                Toast.makeText(
                        MainActivity.this,
                        "A mérés elindult",
                        Toast.LENGTH_SHORT).show();

                if (timer != null) {
                    timer.cancel();
                }
                timer = new Timer();
                timer.schedule(new StopWatchTimerTask(), 0, 1000);
            }
        });

        btnPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeStamp();
                /*TextView tvData = new TextView(MainActivity.this);
                tvData.setText(diff + " sec");
                layoutTimeStamps.addView(tvData, 0);*/
            }
        });

        inflater = (LayoutInflater) getSystemService(
                LAYOUT_INFLATER_SERVICE);

    }

    private void showTimeStamp() {
        int diff = (int)(System.currentTimeMillis() - startTime) / 1000;
        View rowTime = inflater.inflate(R.layout.row_timestamp, null);
        TextView tvTime = (TextView) rowTime.findViewById(R.id.tvTime);
        tvTime.setText(diff + " sec");

        if (layoutTimeStamps.getChildCount() == 0) {
            ImageView ivMedal = (ImageView) rowTime.findViewById(
                    R.id.ivMedal);
            ivMedal.setImageResource(R.drawable.gold_medal);
        }

        layoutTimeStamps.addView(rowTime, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }
}
