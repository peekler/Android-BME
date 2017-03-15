package demo.android.app.hu.prefframeworkdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // itt fogjuk meghívni a saját SettingsActivity-nket
            Intent i = new Intent(this,MySettings.class);
            i.putExtra(":android:no_headers", true);
            i.putExtra(":android:show_fragment", MySettings.FragmentSettingsBasic.class.getName());
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
