package hu.rik.android.animationdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);


        final Animation pushAnim =
                AnimationUtils.loadAnimation(
                        this, R.anim.push_anim);

        pushAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        final Button btnPush = (Button) findViewById(R.id.btnPush);
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPush.startAnimation(pushAnim);
                layoutContent.startAnimation(pushAnim);
            }
        });
    }
}
