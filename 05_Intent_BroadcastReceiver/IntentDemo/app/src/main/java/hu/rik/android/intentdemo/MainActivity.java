package hu.rik.android.intentdemo;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.tvFile)
    TextView tvFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        if (getIntent().getData() != null) {
            String path = getIntent().getData().getPath();
            tvFile.setText(path);
        }

        requestNeededPermission();

    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(MainActivity.this,
                        "I need it for call", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    101);
        } else {
            // már van engedély
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "CALL perm granted", Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(MainActivity.this,
                            "CALL perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    @OnClick(R.id.btnIntentTest)
    public void submit(View v) {
        /*Intent intentTest = new Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:+36201234567")
        );*/

        /*Intent intentTest = new Intent(
                Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI
        );*/

        Intent intentTest = new Intent(Intent.ACTION_WEB_SEARCH);
        intentTest.putExtra(SearchManager.QUERY, "Balaton");

        startActivity(intentTest);
    }


}
