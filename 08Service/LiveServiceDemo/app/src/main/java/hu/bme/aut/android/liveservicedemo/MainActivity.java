package hu.bme.aut.android.liveservicedemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import hu.bme.aut.android.liveservicedemo.service.TimeService;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btnStart = (Button) findViewById(R.id.btnStart);
    btnStart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // service indítás
        startService(new Intent(MainActivity.this, TimeService.class));
      }
    });
    Button btnStop = (Button) findViewById(R.id.btnStop);
    btnStop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // service leállítás
        stopService(new Intent(MainActivity.this, TimeService.class));
      }
    });
  }

}
