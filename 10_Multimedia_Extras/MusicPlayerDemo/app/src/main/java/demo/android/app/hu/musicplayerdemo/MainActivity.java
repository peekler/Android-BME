package demo.android.app.hu.musicplayerdemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.media.MediaPlayer.OnPreparedListener;


public class MainActivity extends Activity implements OnPreparedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(
                        getApplicationContext(),
                        R.raw.test);
                mp.setOnPreparedListener(MainActivity.this);
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.start();

    }
}
