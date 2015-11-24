package hu.bme.aut.android.onclickdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnPress) {
            Toast.makeText(this, "XML onClick demo", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btnButterKnife)
    public void butterKnifeClickDemo(Button button) {
        Toast.makeText(this, "ButterKnife OnClick demo", Toast.LENGTH_LONG).show();
    }

}
